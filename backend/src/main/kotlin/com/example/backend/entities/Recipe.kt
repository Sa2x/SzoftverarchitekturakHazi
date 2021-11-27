package com.example.backend.entities

import java.util.*
import javax.persistence.*

@Entity
data class Recipe(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id:Int=0,
    @Column(unique=true)
    val name:String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",nullable = false)
    val user:User,
    @ManyToMany(mappedBy = "likedRecipes")
    val likes:Set<User>?= emptySet()
){
    override fun hashCode(): Int {
        return Objects.hash(name); //base hash off same as equals the id_address
    }
}
