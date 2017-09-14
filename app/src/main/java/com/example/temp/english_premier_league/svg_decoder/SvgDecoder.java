package com.example.temp.english_premier_league.svg_decoder;

/**
 * Created by temp on 04/09/2017.
 */



        import com.bumptech.glide.load.ResourceDecoder;
        import com.bumptech.glide.load.engine.Resource;
        import com.bumptech.glide.load.resource.SimpleResource;
        import com.caverock.androidsvg.SVG;
        import com.caverock.androidsvg.SVGParseException;

        import java.io.IOException;
        import java.io.InputStream;

/**
 * Decodes an SVG internal representation from an {@link InputStream}.
 */
public class SvgDecoder implements ResourceDecoder<InputStream, SVG> {
    public Resource<SVG> decode(InputStream source, int width, int height) throws IOException {
        try {
            SVG svg = SVG.getFromInputStream(source);
            return new SimpleResource<SVG>(svg);
        } catch (SVGParseException ex) {
            throw new IOException("Cannot load SVG from stream", ex);
        }
    }

    @Override
    public String getId() {
        return "SvgDecoder.com.bumptech.svgsample.app";
    }
}