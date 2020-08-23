package com.github.prgrms.socialserver.global.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

public class IdVO<R, V> {

  private final Class<R> reference;

  private final V value;

  private IdVO(Class<R> reference, V value) {
    this.reference = reference;
    this.value = value;
  }

  public static <R, V> IdVO<R, V> of(Class<R> reference, V value) {
    checkNotNull(reference, "reference must be provided.");
    checkNotNull(value, "value must be provided.");

    return new IdVO<>(reference, value);
  }

  public V value() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    IdVO<?, ?> id = (IdVO<?, ?>) o;
    return Objects.equals(reference, id.reference) &&
      Objects.equals(value, id.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(reference, value);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
      .append("reference", reference.getSimpleName())
      .append("value", value)
      .toString();
  }

}