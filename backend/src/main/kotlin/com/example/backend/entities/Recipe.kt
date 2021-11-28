package com.example.backend.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*
import javax.persistence.*
import kotlin.jvm.Transient

@Entity
data class Recipe(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int = 0,
    @Column(unique = true)
    val name: String,
    @Lob
    val description: String,
    @Transient
    val ingredients: Set<String>,
    @Transient
    val diets: Set<Diet>,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @ManyToMany(mappedBy = "likedRecipes")
    val likes: Set<User>? = emptySet(),

    @JsonIgnore
    @Lob
    val recipePicture: ByteArray?
) {
    override fun hashCode(): Int {
        return Objects.hash(name)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Recipe

        if (id != other.id) return false
        if (name != other.name) return false
        if (user != other.user) return false
        if (likes != other.likes) return false
        if (!recipePicture.contentEquals(other.recipePicture)) return false

        return true
    }
}
