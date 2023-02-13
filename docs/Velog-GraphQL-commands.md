```
query Posts($cursor: ID, $username: String, $temp_only: Boolean, $tag: String, $limit: Int) {
  posts(cursor: $cursor, username: $username, temp_only: $temp_only, tag: $tag, limit: $limit) {
    id
    title
    short_description
    thumbnail
    user {
      id
      username
      profile {
        id
        thumbnail
        __typename
      }
      __typename
    }
    url_slug
    released_at
    updated_at
    comments_count
    tags
    is_private
    likes
    __typename
  }
}

```

```
query UserTags($username: String) {
  userTags(username: $username) {
    tags {
      id
      name
      description
      posts_count
      thumbnail
      __typename
    }
    posts_count
    __typename
  }
}
```
```
mutation PostView($id: ID!) {
  postView(id: $id)
}
```

```
query ReadPost($username: String, $url_slug: String) {
  post(username: $username, url_slug: $url_slug) {
    id
    title
    released_at
    updated_at
    tags
    body
    short_description
    is_markdown
    is_private
    is_temp
    thumbnail
    comments_count
    url_slug
    likes
    liked
    user {
      id
      username
      profile {
        id
        display_name
        thumbnail
        short_bio
        profile_links
        __typename
      }
      velog_config {
        title
        __typename
      }
      __typename
    }
    comments {
      id
      user {
        id
        username
        profile {
          id
          thumbnail
          __typename
        }
        __typename
      }
      text
      replies_count
      level
      created_at
      level
      deleted
      __typename
    }
    series {
      id
      name
      url_slug
      series_posts {
        id
        post {
          id
          title
          url_slug
          user {
            id
            username
            __typename
          }
          __typename
        }
        __typename
      }
      __typename
    }
    linked_posts {
      previous {
        id
        title
        url_slug
        user {
          id
          username
          __typename
        }
        __typename
      }
      next {
        id
        title
        url_slug
        user {
          id
          username
          __typename
        }
        __typename
      }
      __typename
    }
    __typename
  }
}

```

```
query GetRecommendedPosts($id: ID) {
  post(id: $id) {
    recommended_posts {
      id
      title
      short_description
      thumbnail
      likes
      user {
        id
        username
        profile {
          id
          thumbnail
          __typename
        }
        __typename
      }
      url_slug
      released_at
      updated_at
      comments_count
      tags
      is_private
      __typename
    }
    __typename
  }
}

```

```
query UserProfile($username: String!) {
  user(username: $username) {
    id
    username
    profile {
      id
      display_name
      short_bio
      thumbnail
      profile_links
      __typename
    }
    __typename
  }
}

```

```
query GetStats($post_id: ID!) {
  getStats(post_id: $post_id) {
    total
    count_by_day {
      count
      day
      __typename
    }
    __typename
  }
}

```