@echo off
color b

cd ..
cd glowcloud-master\target\
start start.bat
cd ..
cd ..
cd glowcloud-wrapper\target\
start start.bat
echo Started
@ping localhost -n 3 > NUL
exit
