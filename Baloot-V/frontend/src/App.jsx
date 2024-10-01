import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from './pages/Home';
import Layout from "./pages/Layout";
import Commodity from "./pages/Commodity";

function App() {
  return (
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Layout />}>
              <Route index element={<Home />} />
              <Route path="/commodity/:id" element={<Commodity />} />
            {/*<Route path="contact" element={<Contact />} />*/}
            {/*<Route path="*" element={<NoPage />} />*/}
          </Route>
        </Routes>
      </BrowserRouter>
  )
}

export default App;
