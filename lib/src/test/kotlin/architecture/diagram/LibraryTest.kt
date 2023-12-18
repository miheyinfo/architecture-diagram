/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package architecture.diagram

import com.structurizr.Workspace
import com.structurizr.export.IndentingWriter
import com.structurizr.model.InteractionStyle
import com.structurizr.util.WorkspaceUtils

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
            """
                user = person "User" {
                  description ""
                  tags "Element", "Person"
                }
            """.trimIndent(),
            person.toDslString(IndentingWriter()).toString()
        )
    }

    @Test fun `Person with attributes to dsl string creates valid dsl`() {
        val workspace = Workspace("Workspace", "description")
        val person = workspace.model.addPerson("User")
        person.addTags("Tag1", "Tag2", "Tag3")
        person.addProperty("property1", "value1")
        person.addProperty("property2", "value2")
        person.url = "http://example.com"
        person.description = "This is a user"
        assertEquals(
            """
        user = person "User" {
          description "This is a user"
          url "http://example.com"
          tags "Element", "Person", "Tag1", "Tag2", "Tag3"
          properties {
            property1 "value1"
            property2 "value2"
          }
        }
        """.trimIndent(),
            person.toDslString(IndentingWriter()).toString()
        )
    }

    @Test fun `Software System to dsl string creates valid dsl`() {
        val workspace = Workspace("Workspace", "description")
        val softwareSystem = workspace.model.addSoftwareSystem("Software System")
        assertEquals(
            """
                softwareSystem = softwareSystem "Software System" {
                  description ""
                  tags "Element", "Software System"
                }
            """.trimIndent(),
            softwareSystem.toDslString(IndentingWriter()).toString()
        )
    }
    @Test fun `relationship user used software system`() {
        val workspace = Workspace("Workspace", "description")
        val softwareSystem = workspace.model.addSoftwareSystem("Software System")
        val person = workspace.model.addPerson("User")
        person.uses(softwareSystem, "uses")
        assertEquals(
            """
                user -> softwareSystem "uses" {
                  tags "Relationship"
                }
            """.trimIndent(),
            workspace.model.relationships.first().toDslString(IndentingWriter()).toString()
        )
    }

    @Test fun `Relationship to dsl string creates valid dsl`() {
        val workspace = Workspace("Workspace", "description")
        val softwareSystem = workspace.model.addSoftwareSystem("Software System")
        val container1 = softwareSystem.addContainer("Container1", "Description", "Technology")
        val container2 = softwareSystem.addContainer("Container2", "Description", "Technology")
        val component1 = container1.addComponent("Component1", "Description", "Technology")
        val component2 = container2.addComponent("Component2", "Description", "Technology")
        val relationship = component1.uses(component2, "Uses", "HTTP", InteractionStyle.Synchronous, arrayOf("Tag1", "Tag2", "Tag3"))
        relationship?.addProperty("property1", "value1")
        relationship?.addProperty("property2", "value2")
        assertEquals(
            """
        component1 -> component2 "Uses" "HTTP" {
          tags "Relationship", "Synchronous", "Tag1", "Tag2", "Tag3"
          properties {
            property1 "value1"
            property2 "value2"
          }
        }
        """.trimIndent(),
            relationship?.toDslString(IndentingWriter()).toString()
        )
    }

    @Test fun `Container to dsl string container without components`() {
        val workspace = Workspace("Workspace", "description")
        val softwareSystem = workspace.model.addSoftwareSystem("Software System")
        val container = softwareSystem.addContainer("Container", "Description", "Technology")
        assertEquals(
            "container = container \"Container\"",
            container.toDslString(IndentingWriter()).toString()
        )
    }

    @Test fun `Container to dsl string container with component`() {
        val workspace = Workspace("Workspace", "description")
        val softwareSystem = workspace.model.addSoftwareSystem("Software System")
        val container = softwareSystem.addContainer("Container", "Description", "Technology")
        container.addComponent("Component", "Description", "Technology")
        assertEquals(
            """
                container = container "Container" {
                  component = component "Component" {
                    description "Description"
                    technology "Technology"
                    tags "Element", "Component"
                  }
                }
            """.trimIndent(),
            container.toDslString(IndentingWriter()).toString()
        )
    }

    @Test fun `Component to dsl string creates valid dsl`() {
        val workspace = Workspace("Workspace", "description")
        val softwareSystem = workspace.model.addSoftwareSystem("Software System")
        val container = softwareSystem.addContainer("Container", "Description", "Technology")
        val component = container.addComponent("Component", "Description", "Technology")
        component.addTags("Tag1", "Tag2", "Tag3")
        component.addProperty("property1", "value1")
        component.addProperty("property2", "value2")
        assertEquals(
            """
        component = component "Component" {
          description "Description"
          technology "Technology"
          tags "Element", "Component", "Tag1", "Tag2", "Tag3"
          properties {
            property1 "value1"
            property2 "value2"
          }
        }
        """.trimIndent(),
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
                  user = person "User" {
                    description ""
                    tags "Element", "Person"
                  }
                  softwareSystem = softwareSystem "Software System" {
                    description ""
                    tags "Element", "Software System"
                    container = container "Container" {
                      component = component "Component" {
                        description "Description"
                        technology "Technology"
                        tags "Element", "Component"
                      }
                    }
                  }
                  user -> softwareSystem "uses" {
                    tags "Relationship"
                  }
                }
            """.trimIndent(),
            workspace.model.toDslString(IndentingWriter()).toString()
        )
    }

    @Test fun `SystemContext view to dsl string creates valid dsl`() {
        val workspace = Workspace("Workspace", "description")
        val softwareSystem = workspace.model.addSoftwareSystem("Software System")
        val viewSet = workspace.views
        val contextView = viewSet.createSystemContextView(
            softwareSystem, "context", "Cluster Diagram"
        )
        contextView.addAllSoftwareSystems()
        contextView.addAllPeople()
        contextView.enableAutomaticLayout()
        assertEquals(
            """
                systemContext softwareSystem {
                  description "Cluster Diagram"
                  include *
                }
            """.trimIndent(),
            contextView.toDslString(IndentingWriter()).toString()
        )
    }

    @Test fun `SystemContext view with animation to dsl string creates valid dsl`() {
        val workspace = Workspace("Workspace", "description")
        val softwareSystem = workspace.model.addSoftwareSystem("Software System")
        val viewSet = workspace.views
        val contextView = viewSet.createSystemContextView(
            softwareSystem, "context", "Cluster Diagram"
        )
        contextView.addAllSoftwareSystems()
        contextView.addAllPeople()
        contextView.enableAutomaticLayout()
        contextView.addAnimation(softwareSystem)
        assertEquals(
            """
                systemContext softwareSystem {
                  description "Cluster Diagram"
                  include *
                }
            """.trimIndent(),
            contextView.toDslString(IndentingWriter()).toString()
        )
    }

    @Test fun `ComponentView view to dsl string creates valid dsl`() {
        val workspace = Workspace("Workspace", "description")
        val softwareSystem = workspace.model.addSoftwareSystem("Software System")
        val container = softwareSystem.addContainer("Container1", "Description", "Technology")
        val component = container.addComponent("Component", "Description", "Technology")
        val viewSet = workspace.views
        val componentView = viewSet.createComponentView(
            container, "context", "Cluster Diagram"
        )
        componentView.addAllComponents()
        componentView.enableAutomaticLayout()
        componentView.addAnimation(component)
        assertEquals(
            """
                component container1 "context" {
                  description "Cluster Diagram"
                  include *
                }
            """.trimIndent(),
            componentView.toDslString(IndentingWriter()).toString()
        )
    }

    @Test fun `test workspace with SystemContextView view`() {
        val workspace = Workspace("Workspace", "description")
        val softwareSystem = workspace.model.addSoftwareSystem("Software System")
        val container = softwareSystem.addContainer("Container", "Description", "Technology")
        container.addComponent("Component", "Description", "Technology")
        val person = workspace.model.addPerson("User")
        person.uses(softwareSystem, "uses")
        val viewSet = workspace.views
        val contextView = viewSet.createSystemContextView(
            softwareSystem, "context", "Cluster Diagram"
        )
        contextView.addAllSoftwareSystems()
        contextView.addAllPeople()
        contextView.enableAutomaticLayout()
        assertEquals(
            """
                workspace "Workspace" "description" {
                  model {
                    user = person "User" {
                      description ""
                      tags "Element", "Person"
                    }
                    softwareSystem = softwareSystem "Software System" {
                      description ""
                      tags "Element", "Software System"
                      container = container "Container" {
                        component = component "Component" {
                          description "Description"
                          technology "Technology"
                          tags "Element", "Component"
                        }
                      }
                    }
                    user -> softwareSystem "uses" {
                      tags "Relationship"
                    }
                  }
                  views {
                    systemContext softwareSystem {
                      description "Cluster Diagram"
                      include *
                    }
                    styles {
                      element "Person" {
                        shape person
                      }
                      element "Database" {
                        shape cylinder
                      }
                      element "MobileApp" {
                        shape mobileDevicePortrait
                      }
                      element "Browser" {
                        shape webBrowser
                      }
                      element "Pipe" {
                        shape pipe
                      }
                      element "Robot" {
                        shape robot
                      }
                    }
                  }
                }
            """.trimIndent(), workspace.toDslString(IndentingWriter()).toString())
    }


    private fun testWorkspace(): Workspace {
        return WorkspaceUtils.fromJson("""
            {
                "name": "Getting Started",
                "description": "This is a model of my software system.",
                "lastModifiedDate": "2020-03-08T07:24:37Z",
                "model": {
                    "people": [
                        {
                            "id": "1",
                            "tags": "Element,Person",
                            "name": "User",
                            "description": "A user of my software system.",
                            "relationships": [
                                {
                                    "id": "3",
                                    "tags": "Relationship,Synchronous",
                                    "sourceId": "1",
                                    "destinationId": "2",
                                    "description": "Uses",
                                    "interactionStyle": "Synchronous"
                                }
                            ],
                            "location": "Unspecified"
                        }
                    ],
                    "softwareSystems": [
                        {
                            "id": "2",
                            "tags": "Element,Software System",
                            "name": "Software System",
                            "description": "My software system.",
                            "location": "Unspecified"
                        }
                    ],
                    "deploymentNodes": []
                },
                "documentation": {
                    "sections": [],
                    "decisions": [],
                    "images": []
                },
                "views": {
                    "systemContextViews": [
                        {
                            "softwareSystemId": "2",
                            "description": "An example of a System Context diagram.",
                            "key": "SystemContext",
                            "paperSize": "A5_Landscape",
                            "animations": [
                                {
                                    "order": 1,
                                    "elements": [
                                        "1",
                                        "2"
                                    ],
                                    "relationships": [
                                        "3"
                                    ]
                                }
                            ],
                            "enterpriseBoundaryVisible": true,
                            "elements": [
                                {
                                    "id": "1",
                                    "x": 660,
                                    "y": 614
                                },
                                {
                                    "id": "2",
                                    "x": 1370,
                                    "y": 664
                                }
                            ],
                            "relationships": [
                                {
                                    "id": "3"
                                }
                            ]
                        }
                    ],
                    "configuration": {
                        "branding": {},
                        "styles": {
                            "elements": [
                                {
                                    "tag": "Software System",
                                    "background": "#1168bd",
                                    "color": "#ffffff"
                                },
                                {
                                    "tag": "Person",
                                    "background": "#08427b",
                                    "color": "#ffffff",
                                    "shape": "Person"
                                }
                            ],
                            "relationships": []
                        },
                        "terminology": {},
                        "lastSavedView": "SystemContext",
                        "viewSortOrder": "Default",
                        "themes": []
                    },
                    "systemLandscapeViews": [],
                    "containerViews": [],
                    "componentViews": [],
                    "dynamicViews": [],
                    "deploymentViews": [],
                    "filteredViews": []
                }
            }
        """.trimIndent())
    }

}
