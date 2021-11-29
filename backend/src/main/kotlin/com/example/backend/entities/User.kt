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
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL])
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
        return Objects.hash(email) //base hash off same as equals the id_address
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false
        if (userName != other.userName) return false
        if (email != other.email) return false
        if (password != other.password) return false
        if (likedRecipes != other.likedRecipes) return false
        if (uploadedRecipes != other.uploadedRecipes) return false
        if (followedUsers != other.followedUsers) return false
        if (followers != other.followers) return false

        return true
    }
}