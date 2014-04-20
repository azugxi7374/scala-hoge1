echo off

echo compile -> package -> git add -> commit -> push -> cp to lib folder

set IXXASCALALIBCOMMES=
set /P IXXASCALALIBCOMMES="commit message : "

sbt compile & ^
sbt package & ^
git add . & ^
git commit -m %IXXASCALALIBCOMMES% & ^

cp .\target\scala-2.10\ixxascalalib_2.10-0.01.jar %DROPBOX%\Public\liblary
