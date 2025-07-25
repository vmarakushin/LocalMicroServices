package com.example.userservice.controller;

import com.example.userservice.dto.RequestUserDTO;
import com.example.userservice.dto.UserDTO;
import com.example.userservice.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


/**
 * Контроллер для REST взаимодействия с UserService
 *
 * @author vmarakushin
 * @version 2.0
 */
@Tag(name = "User API", description = "Операции с пользователями")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final String CREATE_USER_DESCRIPTION =
            "Создание нового пользователя из UserDTO; сериализация - JSON. " +
                    "ID юзера для создания должен быть равен 0. " +
                    "Смотри схему UserDTO для деталей";

    private final String GET_USER_BY_ID =
            "Возвращает UserDTO с данными по указанному ID.";

    private final String UPDATE_USER =
            "Принимает UserDTO с обновленными данными и локацию с ID обновляемого пользователя."
            ;

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }





    @Operation(summary = "Создать нового пользователя",
            description = CREATE_USER_DESCRIPTION,
            parameters = {
                    @Parameter(
                            name = "UserDTO",
                            description = "DTO с данными пользователя. ID должен быть равен 0.",
                            required = true
                    )
            }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Пользователь успешно создан!",
                    content = @Content(mediaType = "text/plain;charset=UTF-8")),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации данных. Подробности в теле ответа.",
                    content = @Content(mediaType = "text/plain;charset=UTF-8")),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера. Подробности в теле ответа.",
                    content = @Content(mediaType = "text/plain;charset=UTF-8"))
    })
    @PostMapping(consumes = "application/json;charset=UTF-8", produces = "text/plain;charset=UTF-8")

    public ResponseEntity<?> createUser(@RequestBody UserDTO dto) {
        userService.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }






    @Operation(summary = "Получить список всех пользователей")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Вернёт список всех пользователей.",
                    content = @Content(mediaType = "application/json;charset=UTF-8")),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера. Подробности в теле ответа.",
                            content = @Content(mediaType = "text/plain;charset=UTF-8"))
            }
    )
    @GetMapping(produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> getAllUsers() {
        var users = userService.getAllUsers();
        var userModels = users.stream()
                .map(user -> EntityModel.of(user,
                        linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel(),
                        linkTo(methodOn(UserController.class).deleteUser(user.getId())).withRel("delete")
                ))
                .toList();
        var collectionModel = CollectionModel.of(
                userModels,
                linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel()
        );
        return ResponseEntity.ok(collectionModel);
    }






    @Operation(summary = "Получить пользователя по ID",
            description = GET_USER_BY_ID,
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID пользователя для поиска. Должен быть целым числом больше нуля.",
                            required = true,
                            example = "1"
                    )
            }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Вернёт UserDTO с данными пользователя.",
                    content = @Content(mediaType = "application/json;charset=UTF-8",
                            schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации данных. Подробности в теле ответа.",
                    content = @Content(mediaType = "text/plain;charset=UTF-8")),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера. Подробности в теле ответа.",
                    content = @Content(mediaType = "text/plain;charset=UTF-8")),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден!",
                    content = @Content
            ),
    })
    @GetMapping(value = "/{id}", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> getUserById(@PathVariable("id") long id) {
        return userService.getUserById(new RequestUserDTO(id))
                .map(user -> {
                    EntityModel<UserDTO> model = EntityModel.of(user);
                    model.add(linkTo(methodOn(UserController.class).getUserById(id)).withSelfRel());
                    model.add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("users"));
                    model.add(linkTo(methodOn(UserController.class).deleteUser(id)).withRel("delete"));
                    return ResponseEntity.ok(model);
                })
                .orElse(ResponseEntity.notFound().build());
    }







    @Operation(summary = "Обновить пользователя",
            description = UPDATE_USER,
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID пользователя для обновления. " +
                                    "Должен быть целым числом больше нуля, " +
                                    "а также совпадать с ID из UserDTO.",
                            required = true,
                            example = "1"
                    ),
                    @Parameter(
                            name = "UserDTO",
                            description = "DTO с обновленными данными пользователя.",
                            required = true
                    )
            }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное обновление пользователя.",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации данных. Подробности в теле ответа.",
                    content = @Content(mediaType = "text/plain;charset=UTF-8")),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера. Подробности в теле ответа.",
                    content = @Content(mediaType = "text/plain;charset=UTF-8"))
    })
    @PutMapping(value = "/{id}", consumes = "application/json;charset=UTF-8", produces = "text/plain;charset=UTF-8")
    public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody UserDTO dto) {

        if (id != dto.getId()) {
            return ResponseEntity.badRequest().body("ID в пути и теле не совпадают.");
        }
        userService.updateUser(dto);
        return ResponseEntity.ok().build();
    }







    @Operation(summary = "Удалить пользователя",
    description = "Удаление пользователя по указанному ID",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID пользователя для удаления. " +
                                    "Должен быть целым числом больше нуля, ",
                            required = true,
                            example = "1"
                    )
            }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Успешное удаление пользователя.",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации данных. Подробности в теле ответа.",
                    content = @Content(mediaType = "text/plain;charset=UTF-8")),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера. Подробности в теле ответа.",
                    content = @Content(mediaType = "text/plain;charset=UTF-8"))
    })
    @DeleteMapping(value = "/{id}", produces = "text/plain;charset=UTF-8")
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(new RequestUserDTO(id));
        return ResponseEntity.noContent().build();
    }
}