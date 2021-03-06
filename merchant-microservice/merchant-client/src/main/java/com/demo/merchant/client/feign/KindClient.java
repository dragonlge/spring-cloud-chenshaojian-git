package com.demo.merchant.client.feign;

import com.demo.merchant.object.KindQo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient("merchantapi")
public interface KindClient {
    @RequestMapping(method = RequestMethod.GET, value = "/kind/{id}")
    String findById(@RequestParam("id") Long id);

    @RequestMapping(method = RequestMethod.GET, value = "/kind/names/{name}")
    String findByName(@RequestParam("name") String name);

    @RequestMapping(method = RequestMethod.GET, value = "/kind/list")
    String findList();

    @RequestMapping(method = RequestMethod.GET, value = "/kind/page",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String findPage(@RequestParam("index") Integer index, @RequestParam("size") Integer size,
                    @RequestParam("name") String name);

    @RequestMapping(method = RequestMethod.POST, value = "/kind/save",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String create(@RequestBody KindQo kindQo);

    @RequestMapping(method = RequestMethod.PUT, value = "/kind/update",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String update(@RequestBody KindQo kindQo);

    @RequestMapping(method = RequestMethod.DELETE, value = "/kind/delete/{id}")
    String delete(@RequestParam("id") Long id);
}
