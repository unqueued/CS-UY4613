@echo off

SET IP=172.16.29.235
rem echo %IP%

echo "Adding ip alias to catch traffic 192.168.1.106:"
netsh interface ip add address "Local Area Connection" 192.168.1.106 255.0.0.0

echo "reseting any existing portproxies"
netsh interface portproxy reset

echo "setting up port proxy to outside network %IP%"
rem netsh interface portproxy add v4tov4 listenport=12345 connectaddress=10.0.7.216 connectport=12345 listenaddress=192.168.1.106 protocol=tcp

netsh interface portproxy add v4tov4 listenport=12345 connectport=12345 connectaddress=%IP%

