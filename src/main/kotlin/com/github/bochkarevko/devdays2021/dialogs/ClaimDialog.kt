package com.github.bochkarevko.devdays2021.dialogs

import com.github.bochkarevko.devdays2021.MainClass
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JComponent
import com.intellij.openapi.ui.DialogWrapper
import org.jetbrains.annotations.Nullable
import java.nio.file.Path


class ClaimDialog(private val filepath: Path) : DialogWrapper(true) {
    @Nullable
    override fun createCenterPanel(): JComponent {
        val dialogPanel = JPanel(BorderLayout())
        val label = JLabel("You can claim the ownership of file ${filepath.fileName}. Do you want to do it?")
        label.preferredSize = Dimension(100, 100)
        dialogPanel.add(label, BorderLayout.CENTER)
        return dialogPanel
    }

    init {
        init()
        title = "Claim the Ownership"
    }

    override fun doOKAction() {
        super.doOKAction()
        MainClass.manager?.getFileInfo(filepath)?.tryClaimingOwnership()
    }

    override fun doCancelAction() {
        super.doCancelAction()
        MainClass.manager?.getFileInfo(filepath)?.canClaim = false
    }
}
