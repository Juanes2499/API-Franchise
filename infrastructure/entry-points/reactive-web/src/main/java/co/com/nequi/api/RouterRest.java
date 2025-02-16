package co.com.nequi.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {
    @Bean
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(POST("/api/franchise"), handler::createFranchise)
                .and(route(PUT("/api/franchise/name"), handler::updateFranchiseNameById))
                .and(route(POST("/api/branch"), handler::createBranch))
                .andRoute(GET("/api/branchesProducts"), handler::getAllBranchesProducts)
                .andRoute(DELETE("/api/branchesProducts"), handler::deleteBranchProduct)
                .andRoute(PUT("/api/branchesProducts/stock"), handler::updateStockBranchProduct)
                .andRoute(GET("/api/franchise/{franchiseId}/maxStock"), handler::getBranchProductByBranchIdAndProductId);
    }
}
