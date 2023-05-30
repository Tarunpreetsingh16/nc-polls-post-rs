package com.neighborhood.connect.pollspostsrs.models

import com.neighborhood.connect.pollspostsrs.entities.PollOption
import com.neighborhood.connect.pollspostsrs.entities.PollOptionVote
import com.neighborhood.connect.pollspostsrs.entities.Post

data class GetPostsResponse (
    var postsWithPollOptionsAndVotes: MutableList<PostPollOptionsAndVotes>
)

data class PostPollOptionsAndVotes (
    var post: Post,
    var pollOptionAndVotes: MutableList<PollOptionAndVotes>
)

data class PollOptionAndVotes (
    var pollOption: PollOption,
    var pollOptionVotes: List<PollOptionVote?>
)
