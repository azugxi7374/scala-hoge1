echo off

echo "compile -> package -> git add -> commit -> push -> cp to lib folder"

set IXXASCALALIBCOMMES=
set /P IXXASCALALIBCOMMES="commit message : "

echo "--------------------------COMPILE-------------------------" & ^
sbt compile & ^

echo "--------------------------PACKAGE-------------------------" & ^
sbt package & ^

echo "--------------------------GIT ADD-------------------------" & ^
git add . & ^

echo "--------------------------COMMIT-------------------------" & ^
git commit -m %IXXASCALALIBCOMMES% & ^

echo "--------------------------PUSH-------------------------" & ^
git push origin master & ^

echo "--------------------------COPY-------------------------" & ^
cp .\target\scala-2.10\ixxascalalib_2.10-0.01.jar %DROPBOX%\Public\liblary
