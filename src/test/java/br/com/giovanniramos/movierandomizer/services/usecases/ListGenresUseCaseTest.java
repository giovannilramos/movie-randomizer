package br.com.giovanniramos.movierandomizer.services.usecases;

import br.com.giovanniramos.movierandomizer.repositories.GenreRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.List;

import static br.com.giovanniramos.movierandomizer.mocks.GenreMock.genreEntityMock;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListGenresUseCaseTest {
    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private ListGenresUseCase listGenresUseCase;

    @Test
    void shouldListGenresSuccessfully() {
        final var genreEntity = genreEntityMock();

        when(genreRepository.findAll(Sort.by("name"))).thenReturn(List.of(genreEntity));

        final var genreModel = assertDoesNotThrow(() -> listGenresUseCase.execute()).getFirst();

        assertEquals(genreEntity.getId(), genreModel.getId());
        assertEquals(genreEntity.getName(), genreModel.getName());
        assertEquals(genreEntity.getDescription(), genreModel.getDescription());
    }
}