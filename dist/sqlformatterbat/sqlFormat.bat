@echo off
setlocal
echo ����������������������������������������
echo ���@
echo ���@sql-formatter-bat�����@�J�n
echo ���@
echo ����������������������������������������

rem �����`�F�b�N
if "%1" EQU "" (
  echo ---------------------------------------------------------------
  echo.
  echo �f�[�^�i�[�f�B���N�g�����w�肳��Ă��܂���B�������I�����܂��B
  echo.
  echo ---------------------------------------------------------------
  pause
  exit
) else (
  set targetFile=%1
)
rem ���̓t�@�C�������G���R�[�f�B���O
set inputFileEncode=UTF-8
rem SQL Server
set dialect=tsql

rem JAVA_HOME�ݒ�
rem set PATH=[jdk8- directory you downloaded];%PATH%

rem ==============================
rem ���������w�����
rem ------------------------------
rem targetFile            :SqlFormat�Ώۃt�@�C���t���p�X
rem inputFileEncode       :�t�@�C���G���R�[�h�i�f�t�H���gUTF-8�j
rem ==============================
java -cp %CD%\resources\prop;.\resources\lib\sqlformatterbat-0.0.1-SNAPSHOT-all.jar ^
  org.springframework.batch.core.launch.support.CommandLineJobRunner ^
  sqlformatterbat.job0010.AppConfig0010 sqlFormatterBatJob ^
  targetFile=%targetFile% ^
  inputFileEncode=%inputFileEncode% ^
  dialect=%dialect%


echo ����������������������������������������
echo ���@
echo ���@sql-formatter-bat�@�I��
echo ���@
echo ����������������������������������������

pause
rem exit

