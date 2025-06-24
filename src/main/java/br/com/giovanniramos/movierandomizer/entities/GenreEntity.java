package br.com.giovanniramos.movierandomizer.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "genres")
public class GenreEntity {
    @Id
    private String id;
    @Indexed(unique = true)
    private String name;
    private String description;
}
