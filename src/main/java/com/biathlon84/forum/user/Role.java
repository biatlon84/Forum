package com.biathlon84.forum.user;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public enum Role {
    //USER(Set.of(Perm.DEV_READ)),
    USER(new HashSet<>(Arrays.asList(Perm.DEV_READ))),
    ADMIN(new HashSet<>(Arrays.asList(Perm.DEV_READ,Perm.DEV_WRITE)));
    //ADMIN(Stream.of(Perm.DEV_READ,Perm.DEV_WRITE).collect(toSet()));

    private final Set<Perm> permSet ;

    Role(Set<Perm> devWrite) {
        this.permSet = devWrite;
    }
    public Set<Perm> getPermSet() {
        return permSet;
    }
    //private Set<Perm> getSet()
    public Set<SimpleGrantedAuthority> getGrAu(){

        return getPermSet()
                .stream().map(a -> new SimpleGrantedAuthority(a.getPerm()))
                .collect(toSet());
    }
}

