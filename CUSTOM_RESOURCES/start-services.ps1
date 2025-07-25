
$basePath = (Get-Item "$PSScriptRoot\..").FullName
$packVersion = "1.0-SNAPSHOT"


$userServiceJar = Join-Path $basePath "user-service\target\user-service-${packVersion}.jar"
$notificationServiceJar = Join-Path $basePath "notification-service\target\notification-service-${packVersion}.jar"
$discoveryServerJar = Join-Path $basePath "discovery-server\target\discovery-server-${packVersion}.jar"
$apiGatewayJar = Join-Path $basePath "api-gateway\target\api-gateway-${packVersion}.jar"
$configServerJar = Join-Path $basePath "config-server\target\config-server-${packVersion}.jar"



function Start-ServiceInNewWindow($jarPath, $serviceName) {
    Write-Host "Starting $serviceName..."
    Start-Process powershell -ArgumentList "-NoExit", "-Command", "`$host.ui.RawUI.WindowTitle = '$serviceName'; java -jar `"$jarPath`""
}


Start-ServiceInNewWindow $configServerJar "Config Server"
sleep 5
Start-ServiceInNewWindow $discoveryServerJar "Discovery Server"
sleep 5
Start-ServiceInNewWindow $userServiceJar "User Service"
sleep 5
Start-ServiceInNewWindow $notificationServiceJar "Notification Service"
sleep 5
Start-ServiceInNewWindow $apiGatewayJar "Api Gateway"

Write-Host "All services started."