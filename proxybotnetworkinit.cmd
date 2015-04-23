@echo off

SET IP=172.16.27.202
rem echo %IP%

echo "Adding ip alias to catch traffic 192.168.1.106:"
netsh interface ip add address "Local Area Connection" 192.168.1.106 255.0.0.0

echo "removing existing portproxies"
netsh interface portproxy reset

echo "setting up port proxy to outside network %IP%"
netsh interface portproxy add v4tov4 listenport=12345 connectport=12345 connectaddress=%IP%

