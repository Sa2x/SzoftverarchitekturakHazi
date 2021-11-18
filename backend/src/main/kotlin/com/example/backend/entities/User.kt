package com.example.backend.entities

import javax.persistence.*

@Entity
data class User (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id:Int = 0,
    val userName:String,
    val email:String,
    val password:String,
    @ManyToMany
    @JoinTable(
        name = "user_likes",
        joinColumns = [ JoinColumn(name = "user_id") ],
        inverseJoinColumns = [ JoinColumn(name = "recipe_id") ]
    )
    val likedRecipes:List<Recipe>?,
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val uploadedRecipes:List<Recipe>?,
    @ManyToMany
    @JoinTable(
        name = "user_follows",
        joinColumns = [ JoinColumn(name = "follower") ],
        inverseJoinColumns = [ JoinColumn(name = "followed") ]
    )
    val followedUsers:List<User>?,
    @ManyToMany(mappedBy = "followedUsers")
    val followers:List<User>,
)