package com.radioteria.data.entities;

import javax.persistence.*;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Access(AccessType.FIELD)
public class User extends Identifiable<Long> implements Serializable {

    final public static String ID = "id";
    final public static String EMAIL = "email";
    final public static String PASSWORD = "password";
    final public static String NAME = "name";
    final public static String AVATAR_FILE_ID = "avatar_file_id";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = ID, nullable = false, updatable = false)
    private Long id;

    @Column(name = EMAIL, nullable = false, unique = true)
    private String email;

    @Column(name = PASSWORD, nullable = false)
    private String password;

    @Column(name = NAME, nullable = false)
    private String name;

    @ManyToOne(targetEntity = File.class)
    @JoinColumn(name = AVATAR_FILE_ID)
    private File avatarFile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Channel> channels = new HashSet<>();

    public User() {
    }

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Channel> getChannels() {
        return channels;
    }

    public void setChannels(Set<Channel> channels) {
        this.channels = channels;
    }

    public File getAvatarFile() {
        return avatarFile;
    }

    public void setAvatarFile(File avatarFile) {
        this.avatarFile = avatarFile;
    }

    public void addChannel(Channel channel) {
        channel.setUser(this);
        channels.add(channel);
    }

    public void removeChannel(Channel channel) {
        if (channels != null) {
            channels.remove(channel);
        }
    }

}