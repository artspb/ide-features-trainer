package training.ui

import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindowManager
import training.learn.interfaces.Lesson
import training.ui.views.LearnPanel
import training.ui.views.ModulesPanel

object UiManager {

  var learnPanelPerProject = HashMap<Project, LearnPanel>()
  var modulesPanelPerProject = HashMap<Project, ModulesPanel>()

  private val LOG = Logger.getInstance(this.javaClass)

  fun updateToolWindow(project: Project) {
    val windowManager = ToolWindowManager.getInstance(project)
    val learnToolWindow = LearnToolWindowFactory.LEARN_TOOL_WINDOW
    windowManager.getToolWindow(learnToolWindow).contentManager.removeAllContents(false)
    val factory = LearnToolWindowFactory()
    factory.createToolWindowContent(project, windowManager.getToolWindow(learnToolWindow))
    LOG.debug("updateToolWindow")
  }

  fun setLessonView() {
    LearnToolWindowFactory.learnWindowPerProject.keys
        .forEach {
          if (it.isOpen) {
            setLessonViewPerProject(it)
          }
        }
    LOG.debug("setLessonView")
  }

  fun setModulesView() {
    LearnToolWindowFactory.learnWindowPerProject.keys
        .forEach {
          if (it.isOpen) {
            setModulesViewPerProject(it)
          }
        }
    LOG.debug("setModulesView")
  }

  fun updateToolWindowScrollPane() {
    LearnToolWindowFactory.learnWindowPerProject.keys
        .forEach {
          if (it.isOpen) {
            updateToolWindowScrollPanePerProject(it)
          }
        }
    LOG.debug("updateToolWindowScrollPane")
  }

  fun addMessageToLearnPanels(message: String) {
    learnPanelPerProject.values.forEach { it.addMessage(message) }
    LOG.debug("addMessageToLearnPanels: $message")
  }

  fun addMessagesToLearnPanels(messages: Array<Message>) {
    learnPanelPerProject.values.forEach { it.addMessages(messages) }
    LOG.debug("addMessagesToLearnPanels: $messages")
  }

  fun clearLearnPanels() {
    learnPanelPerProject.values.forEach { it.clear() }
    LOG.debug("clearLearnPanels")
  }

  fun clearLessonPanels() {
    learnPanelPerProject.values.forEach { it.clearLessonPanel() }
    LOG.debug("clearLessonPanels")
  }

  fun hideLearnPanelButtons() {
    learnPanelPerProject.values.forEach { it.hideButtons() }
    LOG.debug("hideLearnPanelButtons")
  }

  fun setLessonNameOnLearnPanels(lessonName: String) {
    learnPanelPerProject.values.forEach { it.setLessonName(lessonName) }
    LOG.debug("setLessonNameOnLearnPanels")
  }

  fun setModuleNameOnLearnPanels(moduleName: String) {
    learnPanelPerProject.values.forEach { it.setModuleName(moduleName) }
    LOG.debug("setModuleNameOnLearnPanels")
  }

  fun initLessonOnLearnPanels(lesson: Lesson) {
    learnPanelPerProject.values.forEach { it.modulePanel?.init(lesson) ?: LOG.warn("ModulePanel is null") }
    LOG.debug("initLessonOnLearnPanels")
  }

  fun setButtonSkipActionOnLearnPanels(runnable: Runnable?, text: String?, visible: Boolean) {
    learnPanelPerProject.values.forEach {
      it.setButtonSkipActionAndText(runnable, text, visible)
      it.updateButtonUi()
    }
    LOG.debug("setButtonSkipActionOnLearnPanels")
  }

  fun setButtonNextActionOnLearnPanels(runnable: Runnable, notPassedLesson: Lesson?) {
    learnPanelPerProject.values.forEach {
      it.setButtonNextAction(runnable, notPassedLesson)
      it.updateButtonUi()
    }
    LOG.debug("setButtonNextActionOnLearnPanels")
  }

  fun setButtonNextActionOnLearnPanels(runnable: Runnable, notPassedLesson: Lesson?, text: String?) {
    learnPanelPerProject.values.forEach {
      it.setButtonNextAction(runnable, notPassedLesson, text)
      it.updateButtonUi()
    }
    LOG.debug("setButtonNextActionOnLearnPanels")
  }

  fun hideNextButtonOnLearnPanels() {
    learnPanelPerProject.values.forEach { it.hideNextButton() }
    LOG.debug("hideNextButtonOnLearnPanels")
  }

  fun setPreviousMessagePassedOnLearnPanels() {
    learnPanelPerProject.values.forEach { it.setPreviousMessagesPassed() }
    LOG.debug("setPreviousMessagePassedOnLearnPanels")
  }

  fun setLessonPassedOnLearnPanels() {
    learnPanelPerProject.values.forEach { it.setLessonPassed() }
    LOG.debug("setLessonPassedOnLearnPanels")
  }

  fun updateLessonsForPanels(lesson: Lesson) {
    learnPanelPerProject.values.forEach { it.modulePanel?.updateLessons(lesson) ?: LOG.warn("ModulePanel is null") }
    LOG.debug("updateLessonsForPanels")
  }

  private fun setLessonViewPerProject(project: Project) {
    val myLearnToolWindow = LearnToolWindowFactory.learnWindowPerProject[project] ?: return
    val scrollPane = myLearnToolWindow.scrollPane ?: return
    scrollPane.setViewportView(learnPanelPerProject[project])
    scrollPane.revalidate()
    scrollPane.repaint()
  }

  private fun setModulesViewPerProject(project: Project) {
    val modulesPanel = modulesPanelPerProject[project]
    modulesPanel!!.updateMainPanel()
    val myLearnToolWindow = LearnToolWindowFactory.learnWindowPerProject[project] ?: return
    val scrollPane = myLearnToolWindow.scrollPane ?: return
    scrollPane.setViewportView(modulesPanel)
    scrollPane.revalidate()
    scrollPane.repaint()
  }

  private fun updateToolWindowScrollPanePerProject(project: Project) {
    val myLearnToolWindow = LearnToolWindowFactory.learnWindowPerProject[project] ?: return
    val scrollPane = myLearnToolWindow.scrollPane ?: return
    scrollPane.viewport.revalidate()
    scrollPane.viewport.repaint()
    scrollPane.revalidate()
    scrollPane.repaint()
  }

}