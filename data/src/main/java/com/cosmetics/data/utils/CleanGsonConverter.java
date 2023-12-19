package com.cosmetics.data.utils;

import com.cosmetics.domain.base.BaseEmptyResponse;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.MalformedJsonException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Converter;
import retrofit2.Retrofit;


public class CleanGsonConverter extends Converter.Factory {

    private final Gson gson;

    private CleanGsonConverter(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
    }

    public static CleanGsonConverter create() {
        return create(new Gson());
    }

    public static CleanGsonConverter create(Gson gson) {
        return new CleanGsonConverter(gson);
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new GsonResponseBodyConverter<>(gson, adapter);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new GsonRequestBodyConverter<>(gson, adapter);
    }


    final class GsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
        private final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
        private final Charset UTF_8 = StandardCharsets.UTF_8;

        private final Gson gson;
        private final TypeAdapter<T> adapter;

        GsonRequestBodyConverter(Gson gson, TypeAdapter<T> adapter) {
            this.gson = gson;
            this.adapter = adapter;
        }

        @Override
        public RequestBody convert(T value) throws IOException {
            Buffer buffer = new Buffer();
            Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
            JsonWriter jsonWriter = gson.newJsonWriter(writer);
            adapter.write(jsonWriter, value);
            jsonWriter.close();
            return RequestBody.create(MEDIA_TYPE, buffer.readByteString());
        }
    }

    final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
        private final Gson gson;
        private final TypeAdapter<T> adapter;

        GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
            this.gson = gson;
            this.adapter = adapter;
        }

        @Override
        public T convert(ResponseBody value) throws IOException {
            //Handling issues from the shitty backend API from Ankan(Lazy backend guy)
            String jsonString = value.string();
            try {
                return adapter.fromJson(jsonString);
            } catch (JsonSyntaxException exp) {
                try {
                    JSONObject json = new JSONObject(jsonString);
                    if (json.get("success").toString().equals("1")) {
                        json.get("data");
                        if (json.get("data") instanceof JSONArray) {
                            JSONArray jsonArray = (JSONArray) json.get("data");
                            if (jsonArray.length() == 0) {
                                BaseEmptyResponse response = new BaseEmptyResponse();
                                jsonString = gson.toJson(response, BaseEmptyResponse.class);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return adapter.fromJson(jsonString);
            } catch (MalformedJsonException exp) {
                try {
                    jsonString = jsonString.substring(jsonString.indexOf("{"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return adapter.fromJson(jsonString);
            } finally {
                value.close();
            }
        }
    }
}


