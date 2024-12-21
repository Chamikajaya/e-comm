package chamika.product.shared;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> {

    private List<T> content;
    private int currPageNumber;
    private int totalPages;
    private int size;
    private long totalElements;
    private boolean isFirst;
    private boolean isFinal;

}
