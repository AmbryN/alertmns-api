package dev.ambryn.discordtest.beans;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "File")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fil_id", nullable = false)
    private Long id;

    @Column(name = "fil_name")
    private String name;

    @Column(name = "fil_path")
    private String path;

    protected File() {}

    public File(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        File file = (File) o;
        return id.equals(file.id) && name.equals(file.name) && path.equals(file.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, path);
    }
}
