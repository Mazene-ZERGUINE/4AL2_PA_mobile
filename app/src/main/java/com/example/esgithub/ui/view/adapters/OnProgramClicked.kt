package com.example.esgithub.ui.view.adapters

import com.example.esgithub.models.program.ProgramModel

interface OnProgramClicked {
    fun displayProgramDetails(programData: ProgramModel)
}
