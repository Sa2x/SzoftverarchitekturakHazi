package com.example.backend.entities

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*
import javax.persistence.*

@Entity
data class User (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id:Int = 0,
    val userName:String,
    @Column(unique=true)
    val email:String,
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    val password:String?="",
    @ManyToMany
    @JoinTable(
        name = "user_likes",
        joinColumns = [ JoinColumn(name = "user_id") ],
        inverseJoinColumns = [ JoinColumn(name = "recipe_id") ]
    )
    val likedRecipes:Set<Recipe>? = emptySet(),
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val uploadedRecipes:List<Recipe>?= emptyList(),
    @ManyToMany
    @JoinTable(
        name = "user_follows",
        joinColumns = [ JoinColumn(name = "follower") ],
        inverseJoinColumns = [ JoinColumn(name = "followed") ]
    )
    val followedUsers:Set<User>?= emptySet(),
    @ManyToMany(mappedBy = "followedUsers")
    val followers:Set<User>?= emptySet(),
){
    override fun hashCode(): Int {
        return Objects.hash(email); //base hash off same as equals the id_address
    }
}