package br.com.rabbithole.core.builder.commands.generics.sets;

import br.com.rabbithole.RedisLib;
import br.com.rabbithole.core.builder.Query;
import br.com.rabbithole.core.builder.base.Command;
import br.com.rabbithole.core.builder.base.Execute;
import br.com.rabbithole.core.builder.base.actions.Write;
import redis.clients.jedis.Jedis;

import java.util.Optional;

/**
 * @author Felipe Ros
 * @Usage Represents the command Set on Redis database.
 * @since 2.0
 * @version 1.0
 *
 */
public class Set implements Command, Write<String>, Execute<Boolean> {
    private final String key;
    private final String value;

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
    public Optional<Boolean> execute() {
        try (Jedis jedis = RedisLib.getJedis().getResource()) {
            if (RedisLib.inDebug()) RedisLib.getLogger().info("Query: " + commandName() + "has executed!");
            return Optional.of(jedis.set(getKey(), getValue()).equals("OK"));
        } catch (Exception exception) {
            RedisLib.getLogger().error("Query: " + commandName(), exception);
            return Optional.of(false);
        }
    }

    private Set(Builder builder) {
        this.key = builder.key;
        this.value = builder.value;
    }

    private Query<Set> query() {
        return new Query<>(this);
    }

    public static class Builder implements Execute<Boolean> {
        private String key;
        private String value;

        public Builder setKey(String key) {
            this.key = key;
            return this;
        }

        public Builder setValue(String value) {
            this.value = value;
            return this;
        }

        public Query<Set> build() {
            return new Set(this).query();
        }

        @Override
        public Optional<Boolean> execute() {
            return build().getCommand().execute();
        }
    }
}
