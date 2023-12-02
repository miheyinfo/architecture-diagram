package architecture.diagram

import com.structurizr.Workspace
import com.structurizr.export.IndentingWriter
import com.structurizr.model.*


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


