import {useQuery} from "@apollo/client";
import {GET_CITIES} from "./queries"
import {PageableCity} from "./PageableCity";
import {Login} from "./Login";


const App = () => {
    const {loading, error, data} = useQuery(GET_CITIES, {
        variables: {page: 0, size: 10},
    });
    if (loading) return (<div>Loading…</div>);
    if (error) return null;

    return (
        <div className="App">
            <header className="App-header"> Simple City List</header>
            <Login/>
            <PageableCity citiesPerPage={10}
                          initialPage={0}
                          initialPageCount={data.cities.totalPages}
                          initialCities={data.cities.cities}/>
        </div>
    );
}

export default App;
