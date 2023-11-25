package architecture.diagram

import com.structurizr.export.IndentingWriter
import com.structurizr.model.Person
import com.structurizr.model.Relationship
import com.structurizr.model.SoftwareSystem


fun String.toCamelCase(): String {
    return this.split(" ")
        .mapIndexed { index, word ->
            if (index == 0) word.lowercase() else word.replaceFirstChar { it.uppercase() }
        }
        .joinToString("")
}

fun Person.toDslString(identWriter: IndentingWriter): IndentingWriter {
    identWriter.writeLine("${this.name.toCamelCase()} = person \"${this.name}\"")
    this.relationships.map { it.toDsl(identWriter)}
    return identWriter
}

fun SoftwareSystem.toDslString(identWriter: IndentingWriter): IndentingWriter {
    identWriter.writeLine("${this.name.toCamelCase()} = softwareSystem \"${this.name}\"")
    return identWriter
}

fun Relationship.toDsl(identWriter: IndentingWriter) {
    identWriter.writeLine("${this.source.name.toCamelCase()} -> ${this.destination.name.toCamelCase()} \"${this.description}\"")
}

