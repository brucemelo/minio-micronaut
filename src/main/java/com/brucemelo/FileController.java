package com.brucemelo;

import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.micronaut.http.server.types.files.StreamedFile;
import io.minio.ObjectWriteResponse;

import java.io.IOException;

@Controller
public record FileController(MinioService minioService) {

    @Post("/make-bucket")
    public HttpResponse<?> makeBucket() {
        minioService.makeBucket();
        return HttpResponse.ok();
    }

    @Post(consumes = MediaType.MULTIPART_FORM_DATA)
    public HttpResponse<ObjectWriteResponse> upload(CompletedFileUpload fileUpload) throws IOException {
        var putObject = minioService.putObject(fileUpload.getInputStream());
        return HttpResponse.created(putObject);
    }

    @Get("/{name}")
    public HttpResponse<StreamedFile> download(@PathVariable String name) {
        var getObject = minioService.getObject(name);
        var streamedFile = new StreamedFile(getObject, MediaType.APPLICATION_PDF_TYPE);
        return HttpResponse.ok().header(HttpHeaders.ETAG, getObject.headers().get(HttpHeaders.ETAG)).body(streamedFile);
    }

}
