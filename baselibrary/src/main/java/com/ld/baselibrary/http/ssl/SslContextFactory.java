package com.ld.baselibrary.http.ssl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * http证书
 */
public class SslContextFactory {

    private static final String JJDLDXZ_COM_CER =
            "-----BEGIN CERTIFICATE-----\n" +
                    "MIIF8TCCBNmgAwIBAgIMDB6lI8jUwrLlFhuQMA0GCSqGSIb3DQEBCwUAMFAxCzAJ\n" +
                    "BgNVBAYTAkJFMRkwFwYDVQQKExBHbG9iYWxTaWduIG52LXNhMSYwJAYDVQQDEx1H\n" +
                    "bG9iYWxTaWduIFJTQSBPViBTU0wgQ0EgMjAxODAeFw0yMDAzMTMwODIyNTVaFw0y\n" +
                    "MTAzMTQwODIyNTVaMIGEMQswCQYDVQQGEwJDTjEPMA0GA1UECAwG5YyX5LqsMQ8w\n" +
                    "DQYDVQQHDAbljJfkuqwxPDA6BgNVBAoMM+Wkp+WtpumVv++8iOWMl+S6rO+8iee9\n" +
                    "kee7nOaVmeiCsuenkeaKgOaciemZkOWFrOWPuDEVMBMGA1UEAwwMKi5qamxkeHou\n" +
                    "Y29tMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqYWy0uDNr2MZENWG\n" +
                    "558ynF+1AZLDqyMFwF8uuMW2SuhmNlzHmNzvzOrrYkHlaWUNXpvI1ZyuLKChmDA3\n" +
                    "zSh3cGO64CfwHICu+FeY8G1q7yLxrREBh5FMKzmajGzBb6fOH0fexSZ5RLQWUwDw\n" +
                    "l6iulz4SllMNLPhO165IzTe4XJ3atdVPA5C8bKnFnMZtXD3cj178LSVh16UaLWHr\n" +
                    "gSTI9BDD1sLihgJloLvey/bJl+s/jTs94QQ8h7EfciERsCphO3zCIaot5amuxJKX\n" +
                    "TTtD/AanwieekcW/6KHtGYjTE14dmnnCtasK8lTGblBpdWiJ3j0ecpNBTt995xgQ\n" +
                    "dAlsLwIDAQABo4IClDCCApAwDgYDVR0PAQH/BAQDAgWgMIGOBggrBgEFBQcBAQSB\n" +
                    "gTB/MEQGCCsGAQUFBzAChjhodHRwOi8vc2VjdXJlLmdsb2JhbHNpZ24uY29tL2Nh\n" +
                    "Y2VydC9nc3JzYW92c3NsY2EyMDE4LmNydDA3BggrBgEFBQcwAYYraHR0cDovL29j\n" +
                    "c3AuZ2xvYmFsc2lnbi5jb20vZ3Nyc2FvdnNzbGNhMjAxODBWBgNVHSAETzBNMEEG\n" +
                    "CSsGAQQBoDIBFDA0MDIGCCsGAQUFBwIBFiZodHRwczovL3d3dy5nbG9iYWxzaWdu\n" +
                    "LmNvbS9yZXBvc2l0b3J5LzAIBgZngQwBAgIwCQYDVR0TBAIwADAjBgNVHREEHDAa\n" +
                    "ggwqLmpqbGR4ei5jb22CCmpqbGR4ei5jb20wHQYDVR0lBBYwFAYIKwYBBQUHAwEG\n" +
                    "CCsGAQUFBwMCMB8GA1UdIwQYMBaAFPjvf/LNeGeo3m+PJI2I8YcDArPrMB0GA1Ud\n" +
                    "DgQWBBRbPZRCBI8dxJiCrvCzllpY+O13bDCCAQQGCisGAQQB1nkCBAIEgfUEgfIA\n" +
                    "8AB1ALvZ37wfinG1k5Qjl6qSe0c4V5UKq1LoGpCWZDaOHtGFAAABcNL9Sw4AAAQD\n" +
                    "AEYwRAIgUa/8c7MAK1NI3GHoi/nHX6wjwpoY22LF3qDDLYakEmQCIBXUoRp8cZei\n" +
                    "P7FGw2ScqP5bzDN4Qd8oklTdj8fp7LpGAHcAXNxDkv7mq0VEsV6a1FbmEDf71fpH\n" +
                    "3KFzlLJe5vbHDsoAAAFw0v1NlgAABAMASDBGAiEA8piF3DvLZjyIh0cgtL7kw1gV\n" +
                    "xl0eYKBXXxg2ORQVVBECIQCDRHlW9eDomHPCmRKtynXiKg7ITGGSop6y8VB+4f5d\n" +
                    "fTANBgkqhkiG9w0BAQsFAAOCAQEAN8PUJtALzk7l6Zsxty38UTxLMdxD9AMVpE5A\n" +
                    "nP0wjHOOXBFJBVPtPxBagjKsgQYXMMdxXAYixZcnJpWaHm/bb4n2r1vKQo7clmDz\n" +
                    "9VnBX1Z2HK8w0UZqo92Q/tb2Lc1/2dqJJxtKl8XgomM+ZWwuuLTWh8XnxzAeTYhL\n" +
                    "D8Ae/l8786kAJPF271b2Ghp0bfh+tl3DfNWqhZADJTUhAo3an9ayvLlIuf4bPR+S\n" +
                    "9YGwCBhph+p7Gh+ATYT3EN0vunx6347EnUlN76yUm9915EMar/YN7teT+940F4fX\n" +
                    "89xgSksu8m186YJ/9apzyhe+UxvBbkx18mm11YpJ6oHMdyJ7ug==\n" +
                    "-----END CERTIFICATE-----\n" +
                    "-----BEGIN CERTIFICATE-----\n" +
                    "MIIETjCCAzagAwIBAgINAe5fIh38YjvUMzqFVzANBgkqhkiG9w0BAQsFADBMMSAw\n" +
                    "HgYDVQQLExdHbG9iYWxTaWduIFJvb3QgQ0EgLSBSMzETMBEGA1UEChMKR2xvYmFs\n" +
                    "U2lnbjETMBEGA1UEAxMKR2xvYmFsU2lnbjAeFw0xODExMjEwMDAwMDBaFw0yODEx\n" +
                    "MjEwMDAwMDBaMFAxCzAJBgNVBAYTAkJFMRkwFwYDVQQKExBHbG9iYWxTaWduIG52\n" +
                    "LXNhMSYwJAYDVQQDEx1HbG9iYWxTaWduIFJTQSBPViBTU0wgQ0EgMjAxODCCASIw\n" +
                    "DQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAKdaydUMGCEAI9WXD+uu3Vxoa2uP\n" +
                    "UGATeoHLl+6OimGUSyZ59gSnKvuk2la77qCk8HuKf1UfR5NhDW5xUTolJAgvjOH3\n" +
                    "idaSz6+zpz8w7bXfIa7+9UQX/dhj2S/TgVprX9NHsKzyqzskeU8fxy7quRU6fBhM\n" +
                    "abO1IFkJXinDY+YuRluqlJBJDrnw9UqhCS98NE3QvADFBlV5Bs6i0BDxSEPouVq1\n" +
                    "lVW9MdIbPYa+oewNEtssmSStR8JvA+Z6cLVwzM0nLKWMjsIYPJLJLnNvBhBWk0Cq\n" +
                    "o8VS++XFBdZpaFwGue5RieGKDkFNm5KQConpFmvv73W+eka440eKHRwup08CAwEA\n" +
                    "AaOCASkwggElMA4GA1UdDwEB/wQEAwIBhjASBgNVHRMBAf8ECDAGAQH/AgEAMB0G\n" +
                    "A1UdDgQWBBT473/yzXhnqN5vjySNiPGHAwKz6zAfBgNVHSMEGDAWgBSP8Et/qC5F\n" +
                    "JK5NUPpjmove4t0bvDA+BggrBgEFBQcBAQQyMDAwLgYIKwYBBQUHMAGGImh0dHA6\n" +
                    "Ly9vY3NwMi5nbG9iYWxzaWduLmNvbS9yb290cjMwNgYDVR0fBC8wLTAroCmgJ4Yl\n" +
                    "aHR0cDovL2NybC5nbG9iYWxzaWduLmNvbS9yb290LXIzLmNybDBHBgNVHSAEQDA+\n" +
                    "MDwGBFUdIAAwNDAyBggrBgEFBQcCARYmaHR0cHM6Ly93d3cuZ2xvYmFsc2lnbi5j\n" +
                    "b20vcmVwb3NpdG9yeS8wDQYJKoZIhvcNAQELBQADggEBAJmQyC1fQorUC2bbmANz\n" +
                    "EdSIhlIoU4r7rd/9c446ZwTbw1MUcBQJfMPg+NccmBqixD7b6QDjynCy8SIwIVbb\n" +
                    "0615XoFYC20UgDX1b10d65pHBf9ZjQCxQNqQmJYaumxtf4z1s4DfjGRzNpZ5eWl0\n" +
                    "6r/4ngGPoJVpjemEuunl1Ig423g7mNA2eymw0lIYkN5SQwCuaifIFJ6GlazhgDEw\n" +
                    "fpolu4usBCOmmQDo8dIm7A9+O4orkjgTHY+GzYZSR+Y0fFukAj6KYXwidlNalFMz\n" +
                    "hriSqHKvoflShx8xpfywgVcvzfTO3PYkz6fiNJBonf6q8amaEsybwMbDqKWwIX7e\n" +
                    "SPY=\n" +
                    "-----END CERTIFICATE-----\n" +
                    "-----BEGIN CERTIFICATE-----\n" +
                    "MIIETjCCAzagAwIBAgINAe5fFp3/lzUrZGXWajANBgkqhkiG9w0BAQsFADBXMQsw\n" +
                    "CQYDVQQGEwJCRTEZMBcGA1UEChMQR2xvYmFsU2lnbiBudi1zYTEQMA4GA1UECxMH\n" +
                    "Um9vdCBDQTEbMBkGA1UEAxMSR2xvYmFsU2lnbiBSb290IENBMB4XDTE4MDkxOTAw\n" +
                    "MDAwMFoXDTI4MDEyODEyMDAwMFowTDEgMB4GA1UECxMXR2xvYmFsU2lnbiBSb290\n" +
                    "IENBIC0gUjMxEzARBgNVBAoTCkdsb2JhbFNpZ24xEzARBgNVBAMTCkdsb2JhbFNp\n" +
                    "Z24wggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDMJXaQeQZ4Ihb1wIO2\n" +
                    "hMoonv0FdhHFrYhy/EYCQ8eyip0EXyTLLkvhYIJG4VKrDIFHcGzdZNHr9SyjD4I9\n" +
                    "DCuul9e2FIYQebs7E4B3jAjhSdJqYi8fXvqWaN+JJ5U4nwbXPsnLJlkNc96wyOkm\n" +
                    "DoMVxu9bi9IEYMpJpij2aTv2y8gokeWdimFXN6x0FNx04Druci8unPvQu7/1PQDh\n" +
                    "BjPogiuuU6Y6FnOM3UEOIDrAtKeh6bJPkC4yYOlXy7kEkmho5TgmYHWyn3f/kRTv\n" +
                    "riBJ/K1AFUjRAjFhGV64l++td7dkmnq/X8ET75ti+w1s4FRpFqkD2m7pg5NxdsZp\n" +
                    "hYIXAgMBAAGjggEiMIIBHjAOBgNVHQ8BAf8EBAMCAQYwDwYDVR0TAQH/BAUwAwEB\n" +
                    "/zAdBgNVHQ4EFgQUj/BLf6guRSSuTVD6Y5qL3uLdG7wwHwYDVR0jBBgwFoAUYHtm\n" +
                    "GkUNl8qJUC99BM00qP/8/UswPQYIKwYBBQUHAQEEMTAvMC0GCCsGAQUFBzABhiFo\n" +
                    "dHRwOi8vb2NzcC5nbG9iYWxzaWduLmNvbS9yb290cjEwMwYDVR0fBCwwKjAooCag\n" +
                    "JIYiaHR0cDovL2NybC5nbG9iYWxzaWduLmNvbS9yb290LmNybDBHBgNVHSAEQDA+\n" +
                    "MDwGBFUdIAAwNDAyBggrBgEFBQcCARYmaHR0cHM6Ly93d3cuZ2xvYmFsc2lnbi5j\n" +
                    "b20vcmVwb3NpdG9yeS8wDQYJKoZIhvcNAQELBQADggEBACNw6c/ivvVZrpRCb8RD\n" +
                    "M6rNPzq5ZBfyYgZLSPFAiAYXof6r0V88xjPy847dHx0+zBpgmYILrMf8fpqHKqV9\n" +
                    "D6ZX7qw7aoXW3r1AY/itpsiIsBL89kHfDwmXHjjqU5++BfQ+6tOfUBJ2vgmLwgtI\n" +
                    "fR4uUfaNU9OrH0Abio7tfftPeVZwXwzTjhuzp3ANNyuXlava4BJrHEDOxcd+7cJi\n" +
                    "WOx37XMiwor1hkOIreoTbv3Y/kIvuX1erRjvlJDKPSerJpSZdcfL03v3ykzTr1Eh\n" +
                    "kluEfSufFT90y1HonoMOFm8b50bOI7355KKL0jlrqnkckSziYSQtjipIcJDEHsXo\n" +
                    "4HA=\n" +
                    "-----END CERTIFICATE-----";

    private static final String JJDLDXZ_COM_CER_2021_02_26 =
            "-----BEGIN CERTIFICATE-----\n" +
                    "MIIGajCCBVKgAwIBAgIMZJYZyk75nIya55qpMA0GCSqGSIb3DQEBCwUAMFAxCzAJ\n" +
                    "BgNVBAYTAkJFMRkwFwYDVQQKExBHbG9iYWxTaWduIG52LXNhMSYwJAYDVQQDEx1H\n" +
                    "bG9iYWxTaWduIFJTQSBPViBTU0wgQ0EgMjAxODAeFw0yMTAyMjYwMzAxMjlaFw0y\n" +
                    "MjAzMzAwMzAxMjlaMIGEMQswCQYDVQQGEwJDTjEPMA0GA1UECAwG5YyX5LqsMQ8w\n" +
                    "DQYDVQQHDAbljJfkuqwxPDA6BgNVBAoMM+Wkp+WtpumVv++8iOWMl+S6rO+8iee9\n" +
                    "kee7nOaVmeiCsuenkeaKgOaciemZkOWFrOWPuDEVMBMGA1UEAwwMKi5qamxkeHou\n" +
                    "Y29tMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA1vl7dQ7eA/DzWwU4\n" +
                    "M/COb9nqpdCU1tSZJqXT4+lJA0dua6NgkuECR27jjRAN/Zihkbn9sUDwaFwxqJ2h\n" +
                    "o4jqUFnTpnXNP6aa5gG6JEOYjVzmdWcNfPoydtqdHO/IkGpFwZeP1wiYsd/JRkEG\n" +
                    "ihKLFTY78w47TJHG6Vot2g3jXONb+OzoyzROGZ61L4fGB3rKZ4kd7UysKlRkrcPZ\n" +
                    "vQlTgut65PqoFgVJusUyD0RdSOiR7LK0nricrP6ZIU6EKqyEJa1OFrgKmQXWoUOt\n" +
                    "n6IxMoUTSQaGrm8BxDclfoscWCZWS1hZMacULZueq75kt58t0NBgxT+QTz9/H7rY\n" +
                    "8RwmFwIDAQABo4IDDTCCAwkwDgYDVR0PAQH/BAQDAgWgMIGOBggrBgEFBQcBAQSB\n" +
                    "gTB/MEQGCCsGAQUFBzAChjhodHRwOi8vc2VjdXJlLmdsb2JhbHNpZ24uY29tL2Nh\n" +
                    "Y2VydC9nc3JzYW92c3NsY2EyMDE4LmNydDA3BggrBgEFBQcwAYYraHR0cDovL29j\n" +
                    "c3AuZ2xvYmFsc2lnbi5jb20vZ3Nyc2FvdnNzbGNhMjAxODBWBgNVHSAETzBNMEEG\n" +
                    "CSsGAQQBoDIBFDA0MDIGCCsGAQUFBwIBFiZodHRwczovL3d3dy5nbG9iYWxzaWdu\n" +
                    "LmNvbS9yZXBvc2l0b3J5LzAIBgZngQwBAgIwCQYDVR0TBAIwADAjBgNVHREEHDAa\n" +
                    "ggwqLmpqbGR4ei5jb22CCmpqbGR4ei5jb20wHQYDVR0lBBYwFAYIKwYBBQUHAwEG\n" +
                    "CCsGAQUFBwMCMB8GA1UdIwQYMBaAFPjvf/LNeGeo3m+PJI2I8YcDArPrMB0GA1Ud\n" +
                    "DgQWBBStCxBe5w5Jc2/92KX2ILt5q75lGTCCAX0GCisGAQQB1nkCBAIEggFtBIIB\n" +
                    "aQFnAHYAb1N2rDHwMRnYmQCkURX/dxUcEdkCwQApBo2yCJo32RMAAAF33Ei+cwAA\n" +
                    "BAMARzBFAiEA+mXfGtu8tgzXX4Z1ic5yHJ63WRwIHol48LLPbK6FY8ACIGI9F/YH\n" +
                    "PWEC8Vkm5Usk/7dpn7eXHn2NpiPbz6FzKfIcAHYARqVV63X6kSAwtaKJafTzfREs\n" +
                    "QXS+/Um4havy/HD+bUcAAAF33Ei+uwAABAMARzBFAiEAp6MVGZahFVr2MnEkOPdk\n" +
                    "ou8zdzWtXtqK1uTFoPue0i8CIGTpP5iylux1xqTj8noT/YXH1bVMRCHGPD41Y/BM\n" +
                    "apQ4AHUAUaOw9f0BeZxWbbg3eI8MpHrMGyfL956IQpoN/tSLBeUAAAF33Ei+lgAA\n" +
                    "BAMARjBEAiAtdiRbtlwadYS7TXHwZMOtVQs+28RX4x5BF1mgdQPIzgIgAr01g3Qh\n" +
                    "3xLrmYLt11Am2mtDiL0mUn1qG+r75PnWe20wDQYJKoZIhvcNAQELBQADggEBAKQ0\n" +
                    "tx6fUBct41GnZNH8f48i9I3UGXlGgdAJkxmzTcPv77h6aE0ZNm0oTgEfc08BwfLB\n" +
                    "pjUtojndZZOE0p+7o+78VYfIi/R9typfjaXJso7PIhEUGGaohvbRxWzqYHkS3Ujf\n" +
                    "7Qtlh6tkk5jxQnNF+ekycLBHGCxYGr/Jod187eGCfiXy/cSeV0dNFWC1r/eQkvBD\n" +
                    "hlsq7yKpazAqEbJqpaX3P6vhshyLtpOvh7VL3y0/qa3BeCawkIZ1IkTY3B2R8CkJ\n" +
                    "/hGb7Bpple9spSgPXx0qP2ajsc/GSS/67RU+NeONM3bfLIOf3OBshPIOJPbnC4Qj\n" +
                    "ICsnVq5UvnrW0+4/XKA=\n" +
                    "-----END CERTIFICATE-----\n" +
                    "-----BEGIN CERTIFICATE-----\n" +
                    "MIIETjCCAzagAwIBAgINAe5fIh38YjvUMzqFVzANBgkqhkiG9w0BAQsFADBMMSAw\n" +
                    "HgYDVQQLExdHbG9iYWxTaWduIFJvb3QgQ0EgLSBSMzETMBEGA1UEChMKR2xvYmFs\n" +
                    "U2lnbjETMBEGA1UEAxMKR2xvYmFsU2lnbjAeFw0xODExMjEwMDAwMDBaFw0yODEx\n" +
                    "MjEwMDAwMDBaMFAxCzAJBgNVBAYTAkJFMRkwFwYDVQQKExBHbG9iYWxTaWduIG52\n" +
                    "LXNhMSYwJAYDVQQDEx1HbG9iYWxTaWduIFJTQSBPViBTU0wgQ0EgMjAxODCCASIw\n" +
                    "DQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAKdaydUMGCEAI9WXD+uu3Vxoa2uP\n" +
                    "UGATeoHLl+6OimGUSyZ59gSnKvuk2la77qCk8HuKf1UfR5NhDW5xUTolJAgvjOH3\n" +
                    "idaSz6+zpz8w7bXfIa7+9UQX/dhj2S/TgVprX9NHsKzyqzskeU8fxy7quRU6fBhM\n" +
                    "abO1IFkJXinDY+YuRluqlJBJDrnw9UqhCS98NE3QvADFBlV5Bs6i0BDxSEPouVq1\n" +
                    "lVW9MdIbPYa+oewNEtssmSStR8JvA+Z6cLVwzM0nLKWMjsIYPJLJLnNvBhBWk0Cq\n" +
                    "o8VS++XFBdZpaFwGue5RieGKDkFNm5KQConpFmvv73W+eka440eKHRwup08CAwEA\n" +
                    "AaOCASkwggElMA4GA1UdDwEB/wQEAwIBhjASBgNVHRMBAf8ECDAGAQH/AgEAMB0G\n" +
                    "A1UdDgQWBBT473/yzXhnqN5vjySNiPGHAwKz6zAfBgNVHSMEGDAWgBSP8Et/qC5F\n" +
                    "JK5NUPpjmove4t0bvDA+BggrBgEFBQcBAQQyMDAwLgYIKwYBBQUHMAGGImh0dHA6\n" +
                    "Ly9vY3NwMi5nbG9iYWxzaWduLmNvbS9yb290cjMwNgYDVR0fBC8wLTAroCmgJ4Yl\n" +
                    "aHR0cDovL2NybC5nbG9iYWxzaWduLmNvbS9yb290LXIzLmNybDBHBgNVHSAEQDA+\n" +
                    "MDwGBFUdIAAwNDAyBggrBgEFBQcCARYmaHR0cHM6Ly93d3cuZ2xvYmFsc2lnbi5j\n" +
                    "b20vcmVwb3NpdG9yeS8wDQYJKoZIhvcNAQELBQADggEBAJmQyC1fQorUC2bbmANz\n" +
                    "EdSIhlIoU4r7rd/9c446ZwTbw1MUcBQJfMPg+NccmBqixD7b6QDjynCy8SIwIVbb\n" +
                    "0615XoFYC20UgDX1b10d65pHBf9ZjQCxQNqQmJYaumxtf4z1s4DfjGRzNpZ5eWl0\n" +
                    "6r/4ngGPoJVpjemEuunl1Ig423g7mNA2eymw0lIYkN5SQwCuaifIFJ6GlazhgDEw\n" +
                    "fpolu4usBCOmmQDo8dIm7A9+O4orkjgTHY+GzYZSR+Y0fFukAj6KYXwidlNalFMz\n" +
                    "hriSqHKvoflShx8xpfywgVcvzfTO3PYkz6fiNJBonf6q8amaEsybwMbDqKWwIX7e\n" +
                    "SPY=\n" +
                    "-----END CERTIFICATE-----\n" +
                    "-----BEGIN CERTIFICATE-----\n" +
                    "MIIETjCCAzagAwIBAgINAe5fFp3/lzUrZGXWajANBgkqhkiG9w0BAQsFADBXMQsw\n" +
                    "CQYDVQQGEwJCRTEZMBcGA1UEChMQR2xvYmFsU2lnbiBudi1zYTEQMA4GA1UECxMH\n" +
                    "Um9vdCBDQTEbMBkGA1UEAxMSR2xvYmFsU2lnbiBSb290IENBMB4XDTE4MDkxOTAw\n" +
                    "MDAwMFoXDTI4MDEyODEyMDAwMFowTDEgMB4GA1UECxMXR2xvYmFsU2lnbiBSb290\n" +
                    "IENBIC0gUjMxEzARBgNVBAoTCkdsb2JhbFNpZ24xEzARBgNVBAMTCkdsb2JhbFNp\n" +
                    "Z24wggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDMJXaQeQZ4Ihb1wIO2\n" +
                    "hMoonv0FdhHFrYhy/EYCQ8eyip0EXyTLLkvhYIJG4VKrDIFHcGzdZNHr9SyjD4I9\n" +
                    "DCuul9e2FIYQebs7E4B3jAjhSdJqYi8fXvqWaN+JJ5U4nwbXPsnLJlkNc96wyOkm\n" +
                    "DoMVxu9bi9IEYMpJpij2aTv2y8gokeWdimFXN6x0FNx04Druci8unPvQu7/1PQDh\n" +
                    "BjPogiuuU6Y6FnOM3UEOIDrAtKeh6bJPkC4yYOlXy7kEkmho5TgmYHWyn3f/kRTv\n" +
                    "riBJ/K1AFUjRAjFhGV64l++td7dkmnq/X8ET75ti+w1s4FRpFqkD2m7pg5NxdsZp\n" +
                    "hYIXAgMBAAGjggEiMIIBHjAOBgNVHQ8BAf8EBAMCAQYwDwYDVR0TAQH/BAUwAwEB\n" +
                    "/zAdBgNVHQ4EFgQUj/BLf6guRSSuTVD6Y5qL3uLdG7wwHwYDVR0jBBgwFoAUYHtm\n" +
                    "GkUNl8qJUC99BM00qP/8/UswPQYIKwYBBQUHAQEEMTAvMC0GCCsGAQUFBzABhiFo\n" +
                    "dHRwOi8vb2NzcC5nbG9iYWxzaWduLmNvbS9yb290cjEwMwYDVR0fBCwwKjAooCag\n" +
                    "JIYiaHR0cDovL2NybC5nbG9iYWxzaWduLmNvbS9yb290LmNybDBHBgNVHSAEQDA+\n" +
                    "MDwGBFUdIAAwNDAyBggrBgEFBQcCARYmaHR0cHM6Ly93d3cuZ2xvYmFsc2lnbi5j\n" +
                    "b20vcmVwb3NpdG9yeS8wDQYJKoZIhvcNAQELBQADggEBACNw6c/ivvVZrpRCb8RD\n" +
                    "M6rNPzq5ZBfyYgZLSPFAiAYXof6r0V88xjPy847dHx0+zBpgmYILrMf8fpqHKqV9\n" +
                    "D6ZX7qw7aoXW3r1AY/itpsiIsBL89kHfDwmXHjjqU5++BfQ+6tOfUBJ2vgmLwgtI\n" +
                    "fR4uUfaNU9OrH0Abio7tfftPeVZwXwzTjhuzp3ANNyuXlava4BJrHEDOxcd+7cJi\n" +
                    "WOx37XMiwor1hkOIreoTbv3Y/kIvuX1erRjvlJDKPSerJpSZdcfL03v3ykzTr1Eh\n" +
                    "kluEfSufFT90y1HonoMOFm8b50bOI7355KKL0jlrqnkckSziYSQtjipIcJDEHsXo\n" +
                    "4HA=\n" +
                    "-----END CERTIFICATE-----\n";

    private static final String JJLDXZ_COM_CRT =
            "-----BEGIN CERTIFICATE-----\n" +
                    "MIIGGTCCBQGgAwIBAgIQDaqOC37zacgisaCPgDsmpjANBgkqhkiG9w0BAQsFADBe\n" +
                    "MQswCQYDVQQGEwJVUzEVMBMGA1UEChMMRGlnaUNlcnQgSW5jMRkwFwYDVQQLExB3\n" +
                    "d3cuZGlnaWNlcnQuY29tMR0wGwYDVQQDExRHZW9UcnVzdCBSU0EgQ0EgMjAxODAe\n" +
                    "Fw0xOTAzMTUwMDAwMDBaFw0yMDAzMTQxMjAwMDBaMIGAMQswCQYDVQQGEwJDTjEP\n" +
                    "MA0GA1UEBwwG5YyX5LqsMTwwOgYDVQQKDDPlpKflrabplb/vvIjljJfkuqzvvInn\n" +
                    "vZHnu5zmlZnogrLnp5HmioDmnInpmZDlhazlj7gxCzAJBgNVBAsTAklUMRUwEwYD\n" +
                    "VQQDDAwqLmpqbGR4ei5jb20wggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIB\n" +
                    "AQCqtlItGZOZFpsSUeuHgkz8U+BYdQ5ygKtlg8DJVYE2BqA5o8VBkWj61WtqkWNR\n" +
                    "Bryg4cefLKpx6mL1cghuL3+YmrT2nA9aUqD9kAir29C+b+WkfMSTfouc+uwQaizv\n" +
                    "Rx4fa726fpmMJY2I9rFscumLxznvQRfX3Tah4KJ5p9kmcYaajgbzGQIdznxk4HDA\n" +
                    "ZL+8+oH9cZPWm8+HJjMRN+m3MCrozdE6GVoft7p0U8wB5p105RGxQmn8ksFY99gc\n" +
                    "Je8A8D1b49OcM/K5YD38NNslntTxFQoIsBatA7uip01y1wqSGHANIy7X+XAjpNEO\n" +
                    "Y+zXULEYQREknKzVhI3ovINzAgMBAAGjggKuMIICqjAfBgNVHSMEGDAWgBSQWP+w\n" +
                    "nHWoUVR3se3yo0MWOJ5sxTAdBgNVHQ4EFgQUNcwkjNdkU3s2fIfeUQl/akGFeycw\n" +
                    "IwYDVR0RBBwwGoIMKi5qamxkeHouY29tggpqamxkeHouY29tMA4GA1UdDwEB/wQE\n" +
                    "AwIFoDAdBgNVHSUEFjAUBggrBgEFBQcDAQYIKwYBBQUHAwIwPgYDVR0fBDcwNTAz\n" +
                    "oDGgL4YtaHR0cDovL2NkcC5nZW90cnVzdC5jb20vR2VvVHJ1c3RSU0FDQTIwMTgu\n" +
                    "Y3JsMEwGA1UdIARFMEMwNwYJYIZIAYb9bAEBMCowKAYIKwYBBQUHAgEWHGh0dHBz\n" +
                    "Oi8vd3d3LmRpZ2ljZXJ0LmNvbS9DUFMwCAYGZ4EMAQICMHUGCCsGAQUFBwEBBGkw\n" +
                    "ZzAmBggrBgEFBQcwAYYaaHR0cDovL3N0YXR1cy5nZW90cnVzdC5jb20wPQYIKwYB\n" +
                    "BQUHMAKGMWh0dHA6Ly9jYWNlcnRzLmdlb3RydXN0LmNvbS9HZW9UcnVzdFJTQUNB\n" +
                    "MjAxOC5jcnQwCQYDVR0TBAIwADCCAQIGCisGAQQB1nkCBAIEgfMEgfAA7gB1AO5L\n" +
                    "vbd1zmC64UJpH6vhnmajD35fsHLYgwDEe4l6qP3LAAABaX8i/8gAAAQDAEYwRAIg\n" +
                    "RVacetmYTJ6I3CqnmaN4PdRxk5+9gzcdEVxAS14+3wACIEt7tg+5dAd3huK2X03g\n" +
                    "QevborR6VohUBZHVt6lQrtlYAHUAh3W/51l8+IxDmV+9827/Vo1HVjb/SrVgwbTq\n" +
                    "/16ggw8AAAFpfyMBDwAABAMARjBEAiBeA2jnNe7EYB+zdBE9NohY3SAlJREzxFY/\n" +
                    "0IKafqeX1QIgKjV52zJ/wu7udW4xbxg623B97AqK11lGcXNx0Bx4yKowDQYJKoZI\n" +
                    "hvcNAQELBQADggEBAJLNFgdKKTBuJ3dadJI5mR63Ylyg1TCf9+4vhyTHfaodB5vU\n" +
                    "z1eF4PPA2kDkrh5PFcHTjikWHgJi26UWy6NxeBnzKjHu7IYaZoF0iOfwR4AXr2nx\n" +
                    "+aKwGFBsoWOhJClGgpnSXhHLH+EJlDCh2E73IdhmADSdah080HkzR4SRrvEZ0B+v\n" +
                    "erYJae20adYDoV1Az7Ea6zlNdThJYkWJ2hu4Y7Yl7954SRKVs8UdnovlNj+7IHvS\n" +
                    "DLN77re0svgX1kbTP3OoqqDzAt9/XTVsSAUPHkjz6JTbbAnrPsMM/7c+abQ1DUo9\n" +
                    "Q/76MEv6PIYYZURlaKs6XMYHrltHLreidiaES+c=\n" +
                    "-----END CERTIFICATE-----";

    private static final String FULL_CHAIN_RSA_CRT =
            "-----BEGIN CERTIFICATE-----\n" +
                    "MIIGGTCCBQGgAwIBAgIQDaqOC37zacgisaCPgDsmpjANBgkqhkiG9w0BAQsFADBe\n" +
                    "MQswCQYDVQQGEwJVUzEVMBMGA1UEChMMRGlnaUNlcnQgSW5jMRkwFwYDVQQLExB3\n" +
                    "d3cuZGlnaWNlcnQuY29tMR0wGwYDVQQDExRHZW9UcnVzdCBSU0EgQ0EgMjAxODAe\n" +
                    "Fw0xOTAzMTUwMDAwMDBaFw0yMDAzMTQxMjAwMDBaMIGAMQswCQYDVQQGEwJDTjEP\n" +
                    "MA0GA1UEBwwG5YyX5LqsMTwwOgYDVQQKDDPlpKflrabplb/vvIjljJfkuqzvvInn\n" +
                    "vZHnu5zmlZnogrLnp5HmioDmnInpmZDlhazlj7gxCzAJBgNVBAsTAklUMRUwEwYD\n" +
                    "VQQDDAwqLmpqbGR4ei5jb20wggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIB\n" +
                    "AQCqtlItGZOZFpsSUeuHgkz8U+BYdQ5ygKtlg8DJVYE2BqA5o8VBkWj61WtqkWNR\n" +
                    "Bryg4cefLKpx6mL1cghuL3+YmrT2nA9aUqD9kAir29C+b+WkfMSTfouc+uwQaizv\n" +
                    "Rx4fa726fpmMJY2I9rFscumLxznvQRfX3Tah4KJ5p9kmcYaajgbzGQIdznxk4HDA\n" +
                    "ZL+8+oH9cZPWm8+HJjMRN+m3MCrozdE6GVoft7p0U8wB5p105RGxQmn8ksFY99gc\n" +
                    "Je8A8D1b49OcM/K5YD38NNslntTxFQoIsBatA7uip01y1wqSGHANIy7X+XAjpNEO\n" +
                    "Y+zXULEYQREknKzVhI3ovINzAgMBAAGjggKuMIICqjAfBgNVHSMEGDAWgBSQWP+w\n" +
                    "nHWoUVR3se3yo0MWOJ5sxTAdBgNVHQ4EFgQUNcwkjNdkU3s2fIfeUQl/akGFeycw\n" +
                    "IwYDVR0RBBwwGoIMKi5qamxkeHouY29tggpqamxkeHouY29tMA4GA1UdDwEB/wQE\n" +
                    "AwIFoDAdBgNVHSUEFjAUBggrBgEFBQcDAQYIKwYBBQUHAwIwPgYDVR0fBDcwNTAz\n" +
                    "oDGgL4YtaHR0cDovL2NkcC5nZW90cnVzdC5jb20vR2VvVHJ1c3RSU0FDQTIwMTgu\n" +
                    "Y3JsMEwGA1UdIARFMEMwNwYJYIZIAYb9bAEBMCowKAYIKwYBBQUHAgEWHGh0dHBz\n" +
                    "Oi8vd3d3LmRpZ2ljZXJ0LmNvbS9DUFMwCAYGZ4EMAQICMHUGCCsGAQUFBwEBBGkw\n" +
                    "ZzAmBggrBgEFBQcwAYYaaHR0cDovL3N0YXR1cy5nZW90cnVzdC5jb20wPQYIKwYB\n" +
                    "BQUHMAKGMWh0dHA6Ly9jYWNlcnRzLmdlb3RydXN0LmNvbS9HZW9UcnVzdFJTQUNB\n" +
                    "MjAxOC5jcnQwCQYDVR0TBAIwADCCAQIGCisGAQQB1nkCBAIEgfMEgfAA7gB1AO5L\n" +
                    "vbd1zmC64UJpH6vhnmajD35fsHLYgwDEe4l6qP3LAAABaX8i/8gAAAQDAEYwRAIg\n" +
                    "RVacetmYTJ6I3CqnmaN4PdRxk5+9gzcdEVxAS14+3wACIEt7tg+5dAd3huK2X03g\n" +
                    "QevborR6VohUBZHVt6lQrtlYAHUAh3W/51l8+IxDmV+9827/Vo1HVjb/SrVgwbTq\n" +
                    "/16ggw8AAAFpfyMBDwAABAMARjBEAiBeA2jnNe7EYB+zdBE9NohY3SAlJREzxFY/\n" +
                    "0IKafqeX1QIgKjV52zJ/wu7udW4xbxg623B97AqK11lGcXNx0Bx4yKowDQYJKoZI\n" +
                    "hvcNAQELBQADggEBAJLNFgdKKTBuJ3dadJI5mR63Ylyg1TCf9+4vhyTHfaodB5vU\n" +
                    "z1eF4PPA2kDkrh5PFcHTjikWHgJi26UWy6NxeBnzKjHu7IYaZoF0iOfwR4AXr2nx\n" +
                    "+aKwGFBsoWOhJClGgpnSXhHLH+EJlDCh2E73IdhmADSdah080HkzR4SRrvEZ0B+v\n" +
                    "erYJae20adYDoV1Az7Ea6zlNdThJYkWJ2hu4Y7Yl7954SRKVs8UdnovlNj+7IHvS\n" +
                    "DLN77re0svgX1kbTP3OoqqDzAt9/XTVsSAUPHkjz6JTbbAnrPsMM/7c+abQ1DUo9\n" +
                    "Q/76MEv6PIYYZURlaKs6XMYHrltHLreidiaES+c=\n" +
                    "-----END CERTIFICATE-----";

    public static final String[] certificates =
            new String[]{FULL_CHAIN_RSA_CRT,
                    JJLDXZ_COM_CRT,
                    JJDLDXZ_COM_CER,
                    JJDLDXZ_COM_CER_2021_02_26};

    /**
     * 单项认证
     */
    public static SSLSocketFactory createSSLSocketFactory() {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X509");
            // 创建秘钥
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (String certificate : certificates) {
                // 为了不将证书打包进apk，将证书内容定义成字符串常量，再将字符串转为流的形式
                // 方法二：
                // 将证书放入assets
                // certificate = assets.open("fileNmae");
                InputStream is = new ByteArrayInputStream(certificate.getBytes());
                String certificateAlias = Integer.toString(index++);
                // 放入证书
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(is));
                try {
                    is.close();
                } catch (IOException e) {
                }
            }

            // 获取SSL上下文管理对象
            SSLContext sslContext = SSLContext.getInstance("TLS");
            // 创建信任管理器
            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            // 初始化秘钥
            trustManagerFactory.init(keyStore);
            // 初始化信任管理器
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class SafeHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
//            if (BaseUrls.BASE_URL.equals(hostname)
//                    || BaseUrls.BASE_URL_H5.equals(hostname)
//                    || BaseUrls.BASE_URL_IM.equals(hostname)) {//校验hostname是否正确，如果正确则建立连接
            return true;
//            }
//            return false;
        }
    }
}
