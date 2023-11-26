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

}
