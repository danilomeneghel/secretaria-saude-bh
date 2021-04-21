package br.gov.pbh.prodabel.hubsmsa.converter;

import static org.dozer.loader.api.TypeMappingOptions.mapNull;
import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.TypeMappingBuilder;
import org.dozer.loader.api.TypeMappingOptions;

public final class Conversor {

    private static final DozerBeanMapper MAPPER = new DozerBeanMapper();
    private static final Conversor INSTANCE = new Conversor();

    private Conversor() {
        super();
    }

    public static Conversor getInstance(){
        return INSTANCE;
    }

    public <S, T> T converter(S entidade, Class<T> tipoVO) {
        if (entidade == null) {
            return null;
        }
        return MAPPER.map(entidade, tipoVO);
    }

    public <S, T> T converter(S entidade, Class<T> tipoVO, BeanMappingBuilder builder) {
        if (entidade == null) {
            return null;
        }
        MAPPER.addMapping(builder);
        return MAPPER.map(entidade, tipoVO);
    }

    public <S, T> T converter(final S entidade, final Class<T> tipoVO, final String... excludeFields) {
        if (entidade == null) {
            return null;
        }

        BeanMappingBuilder builder = new BeanMappingBuilder() {
            @Override
            protected void configure() {
                TypeMappingBuilder file = mapping(entidade.getClass(), tipoVO, TypeMappingOptions.oneWay(), mapNull(true));

                for (String field : excludeFields) {
                    file.exclude(field);
                }
            }
        };
        return converter(entidade, tipoVO, builder);
    }
}
