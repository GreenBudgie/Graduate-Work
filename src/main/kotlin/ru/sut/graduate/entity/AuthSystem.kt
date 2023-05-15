package ru.sut.graduate.entity

import ru.sut.graduate.vo.AuthType
import ru.sut.graduate.vo.Browser
import ru.sut.graduate.vo.OS
import javax.persistence.*

@Entity
class AuthSystem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null,
    var name: String? = null,
    //Количество доменов
    var segments: Int? = null,
    //Количество пользователей
    var hosts: Int? = null,
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = OS::class, fetch = FetchType.EAGER)
    var supportedOS: Set<OS> = emptySet(),
    @Enumerated(EnumType.STRING)
    var authType: AuthType? = null,
    var trustFactor: Int? = null,
    var keyLength: Int? = null,
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Browser::class, fetch = FetchType.EAGER)
    var supportedBrowsers: Set<Browser> = emptySet(),
    var supportsMobile: Boolean? = null,
    var supportsDocker: Boolean? = null,
    var price: Int? = null,
) : GenericEntity()