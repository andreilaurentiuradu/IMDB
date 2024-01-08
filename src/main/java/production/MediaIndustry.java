package production;

import java.util.Objects;

public class MediaIndustry {
    public final String value; // title or name

    public MediaIndustry (String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        MediaIndustry that = (MediaIndustry) o;
        return Objects.equals(value, that.value);
    }
}
