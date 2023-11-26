/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package architecture.diagram

import com.structurizr.Workspace
import com.structurizr.export.IndentingWriter

import kotlin.test.Test
import kotlin.test.assertEquals

class LibraryTest {

    @Test fun `test name to camelcase`() {
        assertEquals("mySoftwareSystem", "My Software System".toCamelCase())
    }


    @Test fun `Person to dsl string creates valid dsl`() {
        val workspace = Workspace("Workspace", "description")
        val person = workspace.model.addPerson("User")
        assertEquals(
            "user = person \"User\"",
            person.toDslString(IndentingWriter()).toString()
        )
    }

    @Test fun `Software System to dsl string creates valid dsl`() {
        val workspace = Workspace("Workspace", "description")
        val softwareSystem = workspace.model.addSoftwareSystem("Software System")
        assertEquals(
            "softwareSystem = softwareSystem \"Software System\"",
            softwareSystem.toDslString(IndentingWriter()).toString()
        )
    }
    @Test fun `relationship user used software system`() {
        val workspace = Workspace("Workspace", "description")
        val softwareSystem = workspace.model.addSoftwareSystem("Software System")
        val person = workspace.model.addPerson("User")
        person.uses(softwareSystem, "uses")
        assertEquals(
            "user -> softwareSystem \"uses\"",
            workspace.model.relationships.first().toDslString(IndentingWriter()).toString()
        )
    }

    @Test fun `Container to dsl string creates valid dsl`() {
        val workspace = Workspace("Workspace", "description")
        val softwareSystem = workspace.model.addSoftwareSystem("Software System")
        val container = softwareSystem.addContainer("Container", "Description", "Technology")
        assertEquals(
            "container = container \"Container\"",
            container.toDslString(IndentingWriter()).toString()
        )
    }

    @Test fun `Component to dsl string creates valid dsl`() {
        val workspace = Workspace("Workspace", "description")
        val softwareSystem = workspace.model.addSoftwareSystem("Software System")
        val container = softwareSystem.addContainer("Container", "Description", "Technology")
        val component = container.addComponent("Component", "Description", "Technology")
        assertEquals(
            "component = component \"Component\"",
            component.toDslString(IndentingWriter()).toString()
        )
    }

    @Test fun `Model to dsl string creates valid dsl`() {
        val workspace = Workspace("Workspace", "description")
        val softwareSystem = workspace.model.addSoftwareSystem("Software System")
        val container = softwareSystem.addContainer("Container", "Description", "Technology")
        container.addComponent("Component", "Description", "Technology")
        val person = workspace.model.addPerson("User")
        person.uses(softwareSystem, "uses")
        assertEquals(
            """
                model {
                  user = person "User"
                  softwareSystem = softwareSystem "Software System" {
                    container = container "Container" {
                      component = component "Component"
                    }
                  }
                  user -> softwareSystem "uses"
                }
            """.trimIndent(),
            workspace.model.toDslString(IndentingWriter()).toString()
        )
    }

    @Test fun `Workspace to dsl string creates valid dsl`() {
        val workspace = Workspace("Workspace", "description")
        val softwareSystem = workspace.model.addSoftwareSystem("Software System")
        val container = softwareSystem.addContainer("Container", "Description", "Technology")
        container.addComponent("Component", "Description", "Technology")
        val person = workspace.model.addPerson("User")
        person.uses(softwareSystem, "uses")
        assertEquals(
            """
                workspace "Workspace" "description"{
                  model {
                    user = person "User"
                    softwareSystem = softwareSystem "Software System" {
                      container = container "Container" {
                        component = component "Component"
                      }
                    }
                    user -> softwareSystem "uses"
                  }
                }
            """.trimIndent(),
            workspace.toDslString(IndentingWriter()).toString()
        )
    }

}
