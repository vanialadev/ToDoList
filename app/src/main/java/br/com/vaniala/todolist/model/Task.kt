package br.com.vaniala.todolist.model

data class Task(
    var title: String,
    var date: String,
    var hour: String,
    var id: Int = 0
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Task

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }
}
