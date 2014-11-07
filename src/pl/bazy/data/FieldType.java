package pl.bazy.data;

import java.io.Serializable;

/**
 * Created by Art on 2014-11-07.
 */
public interface FieldType extends Comparable, Serializable {
    String serialize();
}
