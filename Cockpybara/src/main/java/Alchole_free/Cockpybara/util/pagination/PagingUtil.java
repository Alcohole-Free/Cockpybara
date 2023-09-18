package Alchole_free.Cockpybara.util.pagination;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public abstract class PagingUtil{
    public static <T> Page<T> listToPage(List<T> list, Pageable pageable){
        int start=(int)pageable.getOffset();
        int end=Math.min(start+pageable.getPageSize(), list.size());
        return new PageImpl<>(list.subList(start, end), pageable, list.size());
    }
}
