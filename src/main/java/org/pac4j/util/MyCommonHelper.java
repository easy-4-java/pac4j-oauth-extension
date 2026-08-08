package org.pac4j.util;

/**
 * Small utility holder used by the pac4j-oauth-extension modules for formatting
 * human-readable diagnostic strings.
 *
 * <p>The class is intentionally stateless; it merely centralises the formatting
 * rules so that {@link Object#toString()} overrides in the
 * {@link org.pac4j.scribe.model} package can produce consistent output.</p>
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 3.0.0
 * @see org.pac4j.scribe.model.YibanToken
 */
public class MyCommonHelper {

    /**
     * Build a "nice toString" representation for an arbitrary object.
     *
     * <p>The format is
     * <pre>
     * #{SimpleClassName}# | key: value | key: value ...
     * </pre>
     * Arguments are interpreted as alternating key/value pairs, starting with
     * a key. The method never throws and always returns a non-null
     * {@link String}.</p>
     *
     * @param clazz the class whose simple name will prefix the output;
     *              typically the concrete class of the owning object.
     * @param args  alternating key/value pairs; the array may be empty but
     *              individual elements are printed using their default
     *              {@code toString()} representation.
     * @return a human-readable, never-null {@link String}.
     */
    public static String toNiceString(final Class<?> clazz, final Object... args) {
        final StringBuilder sb = new StringBuilder();
        sb.append("#");
        sb.append(clazz.getSimpleName());
        sb.append("# |");
        boolean b = true;
        for (final Object arg : args) {
            if (b) {
                sb.append(" ");
                sb.append(arg);
                sb.append(":");
            } else {
                sb.append(" ");
                sb.append(arg);
                sb.append(" |");
            }
            b = !b;
        }
        return sb.toString();
    }
}