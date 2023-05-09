package br.com.rabbithole.core.builder.commands.generics;

import br.com.rabbithole.RedisLib;
import br.com.rabbithole.core.builder.Query;
import br.com.rabbithole.core.builder.base.Command;
import br.com.rabbithole.core.builder.base.Execute;
import br.com.rabbithole.core.builder.base.options.CommandOptions;
import br.com.rabbithole.core.builder.base.actions.Write;
import br.com.rabbithole.core.builder.options.SetOptions;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.commands.ProtocolCommand;
import redis.clients.jedis.params.SetParams;

import java.util.Optional;

public class Set implements Command, Write<String>, CommandOptions<SetOptions>, Execute {
    private final String key;
    private final String value;
    private final SetOptions options;

    @Override
    public String commandName() {
        return "set";
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public SetOptions getOptions() {
        return this.options;
    }

    @Override
    public Optional<?> execute() { //TODO: EM TESTES! SERÁ ALTERADO PARA PROTOCOL COMMAND!
        try (Jedis jedis = RedisLib.getJedis().getResource()) {
            int expireTime = getOptions().getExpire();
            if (options.isIfNotExists()) {
                if (expireTime != 0 ) jedis.set(getKey(), getValue(), SetParams.setParams().ex(expireTime).nx());
                jedis.setnx(getKey(), getValue());
            } else if (options.isIfExists()) {
                if (expireTime != 0) jedis.set(getKey(), getValue(), SetParams.setParams().ex(expireTime).xx());
                jedis.set(getKey(), getValue(), SetParams.setParams().xx());
            } else if (options.isGet()) {
                if (expireTime != 0) return Optional.of(jedis.setGet(getKey(), getValue(), SetParams.setParams().ex(expireTime)));
            } else {
                if (expireTime != 0) jedis.setex(getKey(), expireTime, getValue());
                jedis.set(getKey(), getValue());
            }
            return Optional.of(true);
        } catch (Exception exception) {
            exception.printStackTrace();
            return Optional.of(false);
        }
    }

    private Set(Builder builder) {
        this.key = builder.key;
        this.value = builder.value;
        this.options = builder.options;
    }

    private Query<Set> query() {
        return new Query<>(this);
    }

    public static class Builder implements Execute {
        private String key;
        private String value;
        private SetOptions options;

        public Builder setKey(String key) {
            this.key = key;
            return this;
        }

        public Builder setValue(String value) {
            this.value = value;
            return this;
        }

        public Builder setOptions(SetOptions options) {
            this.options = options;
            return this;
        }

        public Query<Set> build() {
            return new Set(this).query();
        }

        @Override
        public Optional<?> execute() {
            return build().getCommand().execute();
        }
    }
}
