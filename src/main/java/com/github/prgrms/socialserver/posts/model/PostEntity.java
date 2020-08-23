package com.github.prgrms.socialserver.posts.model;

import com.github.prgrms.socialserver.global.model.IdVO;
import com.github.prgrms.socialserver.global.utils.DateUtil;
import com.github.prgrms.socialserver.users.model.UserEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.time.LocalDateTime.now;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public class PostEntity {

  private final Long seq;

  private final IdVO<UserEntity, Long> userId;

  private String contents;

  private int likes;

  private boolean likesOfMe;

  private int comments;

  private final WriterVO writerVO;

  private final LocalDateTime createAt;

  private String createDt;

  public PostEntity(IdVO<UserEntity, Long> userId, WriterVO writerVO, String contents) {
    this(null, userId, contents, 0, false, 0, writerVO, null);
  }

  public PostEntity(Long seq, IdVO<UserEntity, Long> userId, String contents, int likes, boolean likesOfMe, int comments, WriterVO writerVO, LocalDateTime createAt) {
    checkNotNull(userId, "userId must be provided.");
    checkArgument(isNotEmpty(contents), "contents must be provided.");
    checkArgument(
      contents.length() >= 4 && contents.length() <= 500,
      "post contents length must be between 4 and 500 characters."
    );

    this.seq = seq;
    this.userId = userId;
    this.contents = contents;
    this.likes = likes;
    this.likesOfMe = likesOfMe;
    this.comments = comments;
    this.writerVO = writerVO;
    this.createAt = defaultIfNull(createAt, now());
  }

  public void modify(String contents) {
    checkArgument(isNotEmpty(contents), "contents must be provided.");
    checkArgument(
      contents.length() >= 4 && contents.length() <= 500,
      "post contents length must be between 4 and 500 characters."
    );

    this.contents = contents;
  }

  public int incrementAndGetLikes() {
    return ++likes;
  }

  public int incrementAndGetComments() {
    return ++comments;
  }

  public Long getSeq() {
    return seq;
  }

  public IdVO<UserEntity, Long> getUserId() {
    return userId;
  }

  public String getContents() {
    return contents;
  }

  public int getLikes() {
    return likes;
  }

  public boolean isLikesOfMe() {
    return likesOfMe;
  }

  public int getComments() {
    return comments;
  }

  public Optional<WriterVO> getWriter() {
    return ofNullable(writerVO);
  }

  public LocalDateTime getCreateAt() {
    return createAt;
  }

  public String getCreateDt() {
    return DateUtil.convertToLocalString(createAt);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PostEntity postEntity = (PostEntity) o;
    return Objects.equals(seq, postEntity.seq);
  }

  @Override
  public int hashCode() {
    return Objects.hash(seq);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
      .append("seq", seq)
      .append("userId", userId)
      .append("contents", contents)
      .append("likes", likes)
      .append("likesOfMe", likesOfMe)
      .append("comments", comments)
      .append("writer", writerVO)
      .append("createAt", createAt)
      .toString();
  }

  static public class Builder {
    private Long seq;
    private IdVO<UserEntity, Long> userId;
    private String contents;
    private int likes;
    private boolean likesOfMe;
    private int comments;
    private WriterVO writerVO;
    private LocalDateTime createAt;

    public Builder() {
    }

    public Builder(PostEntity postEntity) {
      this.seq = postEntity.seq;
      this.userId = postEntity.userId;
      this.contents = postEntity.contents;
      this.likes = postEntity.likes;
      this.likesOfMe = postEntity.likesOfMe;
      this.comments = postEntity.comments;
      this.writerVO = postEntity.writerVO;
      this.createAt = postEntity.createAt;
    }

    public Builder seq(Long seq) {
      this.seq = seq;
      return this;
    }

    public Builder userId(IdVO<UserEntity, Long> userId) {
      this.userId = userId;
      return this;
    }

    public Builder contents(String contents) {
      this.contents = contents;
      return this;
    }

    public Builder likes(int likes) {
      this.likes = likes;
      return this;
    }

    public Builder likesOfMe(boolean likesOfMe) {
      this.likesOfMe = likesOfMe;
      return this;
    }

    public Builder comments(int comments) {
      this.comments = comments;
      return this;
    }

    public Builder writer(WriterVO writerVO) {
      this.writerVO = writerVO;
      return this;
    }

    public Builder createAt(LocalDateTime createAt) {
      this.createAt = createAt;
      return this;
    }

    public PostEntity build() {
      return new PostEntity(seq, userId, contents, likes, likesOfMe, comments, writerVO, createAt);
    }
  }

}