# name the installer
# OutFile "Installer.exe" // is given though pom.xml
 
RequestExecutionLevel highest

InstallDir "$PROGRAMFILES64\Daily Backup"
 
# default section start; every NSIS script has at least one section.
Section

	SetOutPath $INSTDIR

	File target\dailybackup-setup-0.0.1-SNAPSHOT.jar

	# create the uninstaller
    WriteUninstaller "$INSTDIR\uninstall.exe"
 
    # create a shortcut named "new shortcut" in the start menu programs directory
    # point the new shortcut at the program uninstaller
    CreateShortcut "$SMPROGRAMS\Daily Backup.lnk" "$INSTDIR\uninstall.exe"
    
# default section end
SectionEnd

# uninstaller section start
Section "uninstall"
 
    # first, delete the uninstaller
    Delete "$INSTDIR\uninstall.exe"
 
    # second, remove the link from the start menu
    Delete "$SMPROGRAMS\Daily Backup.lnk"
 
    RMDir $INSTDIR
# uninstaller section end
SectionEnd