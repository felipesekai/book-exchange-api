package br.com.sekai.book_exchange.model

import javax.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name="users")
data class User( @Id
            @Column(name="user_id")
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            var id: Long = 0,
    @Column(nullable = false, length = 180)
var name: String = "",
@Column(nullable = false, unique = true)
var email: String = "",

@Column()
private var password: String? = null,

@Column( name="account_non_expired" )
var accountNonExpired: Boolean? = null,

@Column( name="account_non_locked" )
var accountNonLocked: Boolean? = null,

@Column( name="credentials_non_expired" )
var credentialsNonExpired: Boolean? = null,

@Column( name="enabled" )
var enabled: Boolean? = null,

@ManyToMany(fetch = FetchType.EAGER)
@JoinTable(name="user_permission",
    joinColumns=[JoinColumn(name="id_user")],
    inverseJoinColumns =[JoinColumn(name="id_permission")],
)
var permissions : List<Permission>? = null) : UserDetails{


    val role : List<String?>
        get() {
            val roles: MutableList<String?> = ArrayList()
            permissions!!.forEach {
                roles.add(it.description)
            }
            return roles
        }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return permissions!!
    }

    override fun getPassword(): String {
        return password!!
    }

    override fun getUsername(): String {
        return  email
    }

    override fun isAccountNonExpired(): Boolean {
        return accountNonExpired!!
    }

    override fun isAccountNonLocked(): Boolean {
        return accountNonLocked!!
    }

    override fun isCredentialsNonExpired(): Boolean {
        return  credentialsNonExpired!!
    }

    override fun isEnabled(): Boolean {
        return  enabled!!
    }
//    constructor()
//    constructor(id: Long , name: String, email: String): this(
//        id = id, name = name, email = email,
//    )

}



