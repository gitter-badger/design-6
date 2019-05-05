# Contributing

Table of Contents:

- [Guides](#guides)
  - [Local development](#local-development)
  - [Publish the design system](#publish-the-design-system)
  - [Publish the documentation](#publish-the-documentation)
- [References](#references)
  - [Repository structure](#repository-structure)
  - [Scripts](#scripts)

## Guides

### Local development

### Publish the design system

### Publish the documentation

We use [Github Pages](https://pages.github.com/) to serve the documentation.
Therefore, 

1. Ensure:
    - You are at `master` branch
    - You are at root
    - Your working tree is clean
1. Build with `yarn run docs-build`
1. **IMPORTANT**
1. Publish with `git add docs && git commit -m "docs: build" && git push`

## References

### Repository structure

```
design/

+ core/            Source code of the design system (@anduin/design on npm)
  + /src           + Scala source, will be compiled to JS
  + /style         + CSS source, including its build tool

+ docsMacros/      Scala macros for the documentation

+ docsSrc/         Source code of the documentation (https://anduin.design)
  + /public        + Public assets such as htmls and images
  + /scripts       + Scripts to develop and distribute
  + /src           + Scala source, will be compiled to JS

+-docs/            Build output of the documentation (for Github Pages)
```

### Scripts

These scripts are available at root. Use `yarn run <script-name>` to execute
them.

```
core-style         Generate the design system's CSS

docs-dev-watch     Watch for local development

docs-dev-serve     Start a local dev server to serve the work in process

docs-build         Build a production documentation
```

