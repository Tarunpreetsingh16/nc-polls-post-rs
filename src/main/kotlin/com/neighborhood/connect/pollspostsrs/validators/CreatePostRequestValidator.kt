package com.neighborhood.connect.pollspostsrs.validators

import com.neighborhood.connect.errorhandlerlib.models.FieldErrors
import com.neighborhood.connect.pollspostsrs.models.CreatePostRequest

class CreatePostRequestValidator: GenericValidator() {
    fun validate(createPostRequest: CreatePostRequest): FieldErrors {
        rule(createPostRequest.post.title, "title").isValueBlank()
        rule(createPostRequest.post.description, "description").isValueBlank()

        run breaking@ {
            createPostRequest.pollOptions.forEach{pollOption ->
                val errorMapSize = getFieldErrors().errors.size;
                rule(pollOption.pollOption, "pollOption").isValueBlank()
                errorMapSize != getFieldErrors().errors.size && return@breaking
            }
        }

        rule(createPostRequest.pollOptions, "pollOptions").isArrayListSizeZero()

        return getFieldErrors()
    }
}
