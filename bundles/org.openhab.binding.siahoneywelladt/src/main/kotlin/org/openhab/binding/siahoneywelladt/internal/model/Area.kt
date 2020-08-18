package org.openhab.binding.siahoneywelladt.internal.model

data class Area(val group: Char, val numberInGroup: Int) {
    val identifier: Int

    constructor(identifier: Int) : this('A' + (identifier - 1) / 8, ((identifier - 1) % 8) + 1)


    init {
        require(group in 'A'..'D')
        val maxAreasInGroup = 8
        require(numberInGroup in 1..maxAreasInGroup)
        identifier = (maxAreasInGroup * (group - 'A') + numberInGroup)
    }

    override fun toString(): String {
        return "$group$numberInGroup"
    }
}
