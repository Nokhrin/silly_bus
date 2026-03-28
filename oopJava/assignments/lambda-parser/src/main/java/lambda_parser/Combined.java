package lambda_parser;

import java.util.List;

/**
 * head: integer
 * tail : {[ws] op [ws] integer}
 */
public interface Combined {
    Integer head();
    List<Suffix> tail();
}
