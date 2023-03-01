@echo off
cls
cd "D:\eclipse\workspaces\MC_1.18.1\Project-T2\src\main\resources\assets\projt2"
echo [0] Copying Project-T2 assets to run directory
echo [1] Copying BLOCKSTATES
copy /V /Y "blockstates" "D:\eclipse\workspaces\MC_1.18.1\Project-T2\bin\main\assets\projt2\blockstates"
echo [2] Copying LANG
copy /V /Y "lang" "D:\eclipse\workspaces\MC_1.18.1\Project-T2\bin\main\assets\projt2\lang"
echo [3] Copying MODELS
copy /V /Y "models" "D:\eclipse\workspaces\MC_1.18.1\Project-T2\bin\main\assets\projt2\models"
echo [4] Copying PARTICLES
copy /V /Y "particles" "D:\eclipse\workspaces\MC_1.18.1\Project-T2\bin\main\assets\projt2\particles"
echo [5] Copying SHADERS
copy /V /Y "shaders" "D:\eclipse\workspaces\MC_1.18.1\Project-T2\bin\main\assets\projt2\shaders"
echo [6] Copying SOUNDS
copy /V /Y "sounds" "D:\eclipse\workspaces\MC_1.18.1\Project-T2\bin\main\assets\projt2\sounds"
echo [7] Copying TEXTURES
copy /V /Y "textures" "D:\eclipse\workspaces\MC_1.18.1\Project-T2\bin\main\assets\projt2\textures"
echo [8] Copying SOUNDS.JSON
copy /V /Y "sounds.json" "D:\eclipse\workspaces\MC_1.18.1\Project-T2\bin\main\assets\projt2"

PAUSE