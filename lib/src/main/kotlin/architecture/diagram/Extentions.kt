package architecture.diagram

import com.structurizr.Workspace
import com.structurizr.export.IndentingWriter
import com.structurizr.model.*
import com.structurizr.view.*


fun String.toCamelCase(): String {
    return this.split(" ")
        .mapIndexed { index, word ->
            if (index == 0) word.lowercase() else word.replaceFirstChar { it.uppercase() }
        }
        .joinToString("")
}

fun Person.toDslString(indentingWriter: IndentingWriter): IndentingWriter {
    indentingWriter.writeLine("${this.name.toCamelCase()} = person \"${this.name}\" {")
    indentingWriter.indent()
    indentingWriter.writeLine("description \"${this.description}\"")
    if (!this.url.isNullOrEmpty())
        indentingWriter.writeLine("url \"${this.url}\"")
    indentingWriter.writeLine("tags ${this.getTagsAsSet().joinToString(", ") { "\"$it\"" }}")
    this.getTagsAsSet()
    if (this.properties.isNotEmpty()) {
        indentingWriter.writeLine("properties {")
        indentingWriter.indent()
        this.properties.toSortedMap().forEach { (key, value) ->
            indentingWriter.writeLine("$key \"$value\"")
        }
        indentingWriter.outdent()
        indentingWriter.writeLine("}")
    }
    indentingWriter.outdent()
    indentingWriter.writeLine("}")
    return indentingWriter
}

fun SoftwareSystem.toDslString(indentingWriter: IndentingWriter): IndentingWriter {
    indentingWriter.writeLine("${this.name.toCamelCase()} = softwareSystem \"${this.name}\" {")
    indentingWriter.indent()
    indentingWriter.writeLine("description \"${this.description}\"")
    if (!this.url.isNullOrEmpty())
        indentingWriter.writeLine("url \"${this.url}\"")
    indentingWriter.writeLine("tags ${this.getTagsAsSet().joinToString(", ") { "\"$it\"" }}")
    if (this.properties.isNotEmpty()) {
        indentingWriter.writeLine("properties {")
        indentingWriter.indent()
        this.properties.toSortedMap().forEach { (key, value) ->
            indentingWriter.writeLine("$key \"$value\"")
        }
        indentingWriter.outdent()
        indentingWriter.writeLine("}")
    }
    this.containers.forEach {
        it.toDslString(indentingWriter)
    }
    indentingWriter.outdent()
    indentingWriter.writeLine("}")
    return indentingWriter
}

fun Container.toDslString(indentingWriter: IndentingWriter): IndentingWriter {
    val firstLine = "${this.name.toCamelCase()} = container \"${this.name}\""
    if (this.components.isEmpty()) {
        indentingWriter.writeLine(firstLine)
        return indentingWriter
    }
    indentingWriter.writeLine("$firstLine {")
    indentingWriter.indent()
    this.components.forEach {
        it.toDslString(indentingWriter)
    }
    indentingWriter.outdent()
    indentingWriter.writeLine("}")
    return indentingWriter
}

fun Component.toDslString(indentingWriter: IndentingWriter): IndentingWriter {
    indentingWriter.writeLine("${this.name.toCamelCase()} = component \"${this.name}\" {")
    indentingWriter.indent()
    indentingWriter.writeLine("description \"${this.description}\"")
    indentingWriter.writeLine("technology \"${this.technology}\"")
    indentingWriter.writeLine("tags ${this.getTagsAsSet().joinToString(", ") { "\"$it\"" }}")
    if (this.properties.isNotEmpty()) {
        indentingWriter.writeLine("properties {")
        indentingWriter.indent()
        this.properties.toSortedMap().forEach { (key, value) ->
            indentingWriter.writeLine("$key \"$value\"")
        }
        indentingWriter.outdent()
        indentingWriter.writeLine("}")
    }
    indentingWriter.outdent()
    indentingWriter.writeLine("}")
    return indentingWriter
}

fun Relationship.toDslString(indentingWriter: IndentingWriter): IndentingWriter {
    val technology = if (!this.technology.isNullOrEmpty()) { " \"${this.technology}\"" } else ""
    val description = if (!this.description.isNullOrEmpty()) { " \"${this.description}\"" } else ""
    indentingWriter.writeLine("${this.source.name.toCamelCase()} -> ${this.destination.name.toCamelCase()}$description$technology {")
    indentingWriter.indent()
    indentingWriter.writeLine("tags ${this.getTagsAsSet().joinToString(", ") { "\"$it\"" }}")
    if (this.properties.isNotEmpty()) {
        indentingWriter.writeLine("properties {")
        indentingWriter.indent()
        this.properties.toSortedMap().forEach { (key, value) ->
            indentingWriter.writeLine("$key \"$value\"")
        }
        indentingWriter.outdent()
        indentingWriter.writeLine("}")
    }
    indentingWriter.outdent()
    indentingWriter.writeLine("}")
    return indentingWriter
}
fun Model.toDslString(indentingWriter: IndentingWriter): IndentingWriter {
    indentingWriter.writeLine("model {")
    indentingWriter.indent()
    this.people.forEach { it.toDslString(indentingWriter) }
    this.softwareSystems.forEach { it.toDslString(indentingWriter) }
    this.relationships.forEach { it.toDslString(indentingWriter) }
    indentingWriter.outdent()
    indentingWriter.writeLine("}")
    return indentingWriter
}

fun Workspace.toDslString(indentingWriter: IndentingWriter): IndentingWriter {
    indentingWriter.writeLine("workspace \"${this.name}\" \"${this.description}\" {")
    indentingWriter.indent()
    this.model.toDslString(indentingWriter)
    this.views.toDslString(indentingWriter)
    if (this.properties.isNotEmpty()) {
        indentingWriter.writeLine("properties {")
        indentingWriter.indent()
        this.properties.toSortedMap().forEach { (key, value) ->
            indentingWriter.writeLine("$key \"$value\"")
        }
        indentingWriter.outdent()
        indentingWriter.writeLine("}")
    }
    indentingWriter.outdent()
    indentingWriter.writeLine("}")
    return indentingWriter
}

fun ViewSet.toDslString(indentingWriter: IndentingWriter): IndentingWriter {
    indentingWriter.writeLine("views {")
    indentingWriter.indent()
    this.systemContextViews.forEach { it.toDslString(indentingWriter) }
    this.containerViews.forEach { it.toDslString(indentingWriter) }
    this.componentViews.forEach { it.toDslString(indentingWriter) }
    // TODO Add other views here
//    this.dynamicViews.forEach { it.toDslString(indentingWriter) }
//    this.deploymentViews.forEach { it.toDslString(indentingWriter) }
    indentingWriter.outdent()
    indentingWriter.writeLine("}")
    return indentingWriter
}

fun ComponentView.toDslString(indentingWriter: IndentingWriter): IndentingWriter {
    indentingWriter.writeLine("component ${this.container.name.toCamelCase()} \"${this.key}\" {")
    indentingWriter.indent()
    indentingWriter.writeLine("description \"${this.description}\"")
    // this.elements.forEach { it.toDslString(indentingWriter) }
    // this.relationships.forEach { it.toDslString(indentingWriter) }

    // TODO Add other attributes here

    indentingWriter.writeLine("include *")
    indentingWriter.outdent()
    indentingWriter.writeLine("}")
    return indentingWriter
}

fun ContainerView.toDslString(indentingWriter: IndentingWriter): IndentingWriter {
    indentingWriter.writeLine("container \"${this.softwareSystem.name}\" {")
    indentingWriter.indent()
    indentingWriter.writeLine("description \"${this.description}\"")
//    this.elements.forEach { it.toDslString(indentingWriter) }
//    this.relationships.forEach { it.toDslString(indentingWriter) }

    // TODO Add other attributes here

    indentingWriter.writeLine("include *")
    this.automaticLayout?.toDslString(indentingWriter)
    this.animations.forEach { _ -> toDslString(indentingWriter) }
    indentingWriter.writeLine("title \"${this.title}\"")
    indentingWriter.writeLine("description \"${this.description}\"")
    if (this.properties.isNotEmpty()) {
        indentingWriter.writeLine("properties {")
        indentingWriter.indent()
        this.properties.toSortedMap().forEach { (key, value) ->
            indentingWriter.writeLine("$key \"$value\"")
        }
        indentingWriter.outdent()
        indentingWriter.writeLine("}")
    }
    indentingWriter.outdent()
    indentingWriter.writeLine("}")
    return indentingWriter
}

fun AutomaticLayout.toDslString(indentingWriter: IndentingWriter): IndentingWriter {
    indentingWriter.writeLine("autoLayout")
    // TODO wrong attribute names
//    indentingWriter.writeLine("autoLayout {")
//    indentingWriter.indent()
//    indentingWriter.writeLine("rankDirection \"${this.rankDirection}\"")
//    indentingWriter.writeLine("rankSeparation ${this.rankSeparation}")
//    indentingWriter.writeLine("nodeSeparation ${this.nodeSeparation}")
//    indentingWriter.writeLine("edgeSeparation ${this.edgeSeparation}")
//    indentingWriter.outdent()
//    indentingWriter.writeLine("}")
    return indentingWriter
}

fun Animation.toDslString(indentingWriter: IndentingWriter): IndentingWriter {
    indentingWriter.writeLine("animation {")
    indentingWriter.indent()

    // TODO investigate how it works add other attributes here

    indentingWriter.outdent()
    indentingWriter.writeLine("}")
    return indentingWriter
}


fun ElementView.toDslString(indentingWriter: IndentingWriter): IndentingWriter {
    indentingWriter.writeLine("${this.element.name.toCamelCase()} = element \"${this.element.name}\" {")
    indentingWriter.indent()
    indentingWriter.writeLine("description \"${this.element.description}\"")
    indentingWriter.writeLine("tags ${this.element.getTagsAsSet().joinToString(", ") { "\"$it\"" }}")
    if (this.element.properties.isNotEmpty()) {
        indentingWriter.writeLine("properties {")
        indentingWriter.indent()
        this.element.properties.toSortedMap().forEach { (key, value) ->
            indentingWriter.writeLine("$key \"$value\"")
        }
        indentingWriter.outdent()
        indentingWriter.writeLine("}")
    }
    indentingWriter.outdent()
    indentingWriter.writeLine("}")
    return indentingWriter
}

fun RelationshipView.toDslString(indentingWriter: IndentingWriter): IndentingWriter {
    val technology = if (!this.relationship.technology.isNullOrEmpty()) { " \"${this.relationship.technology}\"" } else ""
    val description = if (!this.relationship.description.isNullOrEmpty()) { " \"${this.relationship.description}\"" } else ""
    indentingWriter.writeLine("${this.relationship.source.name.toCamelCase()} -> ${this.relationship.destination.name.toCamelCase()}$description$technology {")
    indentingWriter.indent()
    indentingWriter.writeLine("tags ${this.relationship.getTagsAsSet().joinToString(", ") { "\"$it\"" }}")
    if (this.relationship.properties.isNotEmpty()) {
        indentingWriter.writeLine("properties {")
        indentingWriter.indent()
        this.relationship.properties.toSortedMap().forEach { (key, value) ->
            indentingWriter.writeLine("$key \"$value\"")
        }
        indentingWriter.outdent()
        indentingWriter.writeLine("}")
    }
    indentingWriter.outdent()
    indentingWriter.writeLine("}")
    return indentingWriter
}


fun SystemContextView.toDslString(indentingWriter: IndentingWriter): IndentingWriter {
    indentingWriter.writeLine("systemContext ${this.softwareSystem.name.toCamelCase()} {")
    indentingWriter.indent()
    indentingWriter.writeLine("description \"${this.description}\"")
    indentingWriter.writeLine("include *")
    indentingWriter.outdent()
    indentingWriter.writeLine("}")
    return indentingWriter
}

fun SystemLandscapeView.toDslString(indentingWriter: IndentingWriter): IndentingWriter {
    indentingWriter.writeLine("systemLandscape ${this.softwareSystem.name} {")
    indentingWriter.indent()
    indentingWriter.writeLine("description \"${this.description}\"")
    indentingWriter.outdent()
    indentingWriter.writeLine("}")
    return indentingWriter
}

